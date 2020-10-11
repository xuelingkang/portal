package com.xzixi.framework.boot.swagger2.extension.component;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.members.ResolvedField;
import com.fasterxml.classmate.members.ResolvedMember;
import com.fasterxml.classmate.members.ResolvedMethod;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.*;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.xzixi.framework.boot.swagger2.extension.util.FieldUtil;
import com.xzixi.framework.boot.swagger2.extension.annotation.IgnoreSwagger2Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.ClassUtils;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.Collections;
import springfox.documentation.schema.Maps;
import springfox.documentation.schema.Types;
import springfox.documentation.schema.property.bean.AccessorsProvider;
import springfox.documentation.schema.property.field.FieldProvider;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.schema.AlternateTypeProvider;
import springfox.documentation.spi.schema.EnumTypeDeterminer;
import springfox.documentation.spi.service.contexts.ParameterExpansionContext;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;
import springfox.documentation.spring.web.readers.parameter.ExpansionContext;
import springfox.documentation.spring.web.readers.parameter.ModelAttributeField;
import springfox.documentation.spring.web.readers.parameter.ModelAttributeParameterExpander;
import springfox.documentation.spring.web.readers.parameter.ModelAttributeParameterMetadataAccessor;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 替换{@link ModelAttributeParameterExpander}
 *
 * @see IgnoreSwagger2Parameter
 * @author 薛凌康
 */
@Primary
public class ModelAttributeParameterExpanderExtension extends ModelAttributeParameterExpander {

    private static final Logger LOG = LoggerFactory.getLogger(ModelAttributeParameterExpanderExtension.class);
    private final FieldProvider fields;
    private final AccessorsProvider accessors;
    private final EnumTypeDeterminer enumTypeDeterminer;
    @Autowired
    protected DocumentationPluginsManager pluginsManager;

    @Autowired
    public ModelAttributeParameterExpanderExtension(FieldProvider fields, AccessorsProvider accessors, EnumTypeDeterminer enumTypeDeterminer) {
        super(fields, accessors, enumTypeDeterminer);
        this.fields = fields;
        this.accessors = accessors;
        this.enumTypeDeterminer = enumTypeDeterminer;
    }

    @Override
    public List<Parameter> expand(ExpansionContext context) {
        List<Parameter> parameters = Lists.newArrayList();
        Set<PropertyDescriptor> propertyDescriptors = this.propertyDescriptors(context.getParamType().getErasedType());
        Map<Method, PropertyDescriptor> propertyLookupByGetter = this.propertyDescriptorsByMethod(context.getParamType().getErasedType(), propertyDescriptors);
        Iterable<ResolvedMethod> getters = FluentIterable.from(this.accessors.in(context.getParamType())).filter(this.onlyValidGetters(propertyLookupByGetter.keySet()));
        Map<String, ResolvedField> fieldsByName = FluentIterable.from(this.fields.in(context.getParamType())).uniqueIndex(new Function<ResolvedField, String>() {
            @Override
            public String apply(ResolvedField input) {
                return input.getName();
            }
        });
        LOG.debug("Expanding parameter type: {}", context.getParamType());
        AlternateTypeProvider alternateTypeProvider = context.getDocumentationContext().getAlternateTypeProvider();
        FluentIterable<ModelAttributeField> attributes = this.allModelAttributes(propertyLookupByGetter, getters, fieldsByName, alternateTypeProvider);
        FluentIterable<ModelAttributeField> expendables = attributes.filter(Predicates.not(this.simpleType())).filter(Predicates.not(this.recursiveType(context)));
        Iterator var10 = expendables.iterator();

        while (var10.hasNext()) {
            ModelAttributeField each = (ModelAttributeField) var10.next();
            LOG.debug("Attempting to expand expandable property: {}", each.getName());
            parameters.addAll(this.expand(context.childContext(this.nestedParentName(context.getParentName(), each), each.getFieldType(), context.getOperationContext())));
        }

        FluentIterable<ModelAttributeField> collectionTypes = attributes.filter(Predicates.and(this.isCollection(), Predicates.not(this.recursiveCollectionItemType(context.getParamType()))));
        Iterator var16 = collectionTypes.iterator();

        while (true) {
            while (var16.hasNext()) {
                ModelAttributeField each = (ModelAttributeField) var16.next();
                LOG.debug("Attempting to expand collection/array field: {}", each.getName());
                ResolvedType itemType = Collections.collectionElementType(each.getFieldType());
                if (!Types.isBaseType(itemType) && !this.enumTypeDeterminer.isEnum(itemType.getErasedType())) {
                    ExpansionContext childContext = context.childContext(this.nestedParentName(context.getParentName(), each), itemType, context.getOperationContext());
                    if (!context.hasSeenType(itemType)) {
                        parameters.addAll(this.expand(childContext));
                    }
                } else {
                    parameters.add(this.simpleFields(context.getParentName(), context, each));
                }
            }

            FluentIterable<ModelAttributeField> simpleFields = attributes.filter(this.simpleType());
            Iterator var18 = simpleFields.iterator();

            while (var18.hasNext()) {
                ModelAttributeField each = (ModelAttributeField) var18.next();
                parameters.add(this.simpleFields(context.getParentName(), context, each));
            }

            return FluentIterable.from(parameters).filter(Predicates.not(this.hiddenParameters())).filter(Predicates.not(this.voidParameters())).toList();
        }
    }

    private FluentIterable<ModelAttributeField> allModelAttributes(Map<Method, PropertyDescriptor> propertyLookupByGetter, Iterable<ResolvedMethod> getters, Map<String, ResolvedField> fieldsByName, AlternateTypeProvider alternateTypeProvider) {
        FluentIterable<ModelAttributeField> modelAttributesFromGetters = FluentIterable.from(getters).transform(this.toModelAttributeField(fieldsByName, propertyLookupByGetter, alternateTypeProvider));
        FluentIterable<ModelAttributeField> modelAttributesFromFields = FluentIterable.from(fieldsByName.values()).filter(this.publicFields()).transform(this.toModelAttributeField(alternateTypeProvider));
        return FluentIterable.from(Sets.union(modelAttributesFromFields.toSet(), modelAttributesFromGetters.toSet()));
    }

    private Function<ResolvedField, ModelAttributeField> toModelAttributeField(final AlternateTypeProvider alternateTypeProvider) {
        return new Function<ResolvedField, ModelAttributeField>() {
            @Override
            public ModelAttributeField apply(ResolvedField input) {
                return new ModelAttributeField(alternateTypeProvider.alternateFor(input.getType()), input.getName(), input, input);
            }
        };
    }

    private Predicate<ResolvedField> publicFields() {
        return new Predicate<ResolvedField>() {
            @Override
            public boolean apply(ResolvedField input) {
                return input.isPublic();
            }
        };
    }

    private Predicate<Parameter> voidParameters() {
        return new Predicate<Parameter>() {
            @Override
            public boolean apply(Parameter input) {
                return Types.isVoid((ResolvedType) input.getType().orNull());
            }
        };
    }

    private Predicate<ModelAttributeField> recursiveCollectionItemType(final ResolvedType paramType) {
        return new Predicate<ModelAttributeField>() {
            @Override
            public boolean apply(ModelAttributeField input) {
                return Objects.equal(Collections.collectionElementType(input.getFieldType()), paramType);
            }
        };
    }

    private Predicate<Parameter> hiddenParameters() {
        return new Predicate<Parameter>() {
            @Override
            public boolean apply(Parameter input) {
                return input.isHidden();
            }
        };
    }

    private Parameter simpleFields(String parentName, ExpansionContext context, ModelAttributeField each) {
        LOG.debug("Attempting to expand field: {}", each);
        String dataTypeName = (String) Optional.fromNullable(Types.typeNameFor(each.getFieldType().getErasedType())).or(each.getFieldType().getErasedType().getSimpleName());
        LOG.debug("Building parameter for field: {}, with type: ", each, each.getFieldType());
        ParameterExpansionContext parameterExpansionContext = new ParameterExpansionContext(dataTypeName, parentName, ParameterTypeDeterminer.determineScalarParameterType(context.getOperationContext().consumes(), context.getOperationContext().httpMethod()), new ModelAttributeParameterMetadataAccessor(each.annotatedElements(), each.getFieldType(), each.getName()), context.getDocumentationContext().getDocumentationType(), new ParameterBuilder());
        return this.pluginsManager.expandParameter(parameterExpansionContext);
    }

    private Predicate<ModelAttributeField> recursiveType(final ExpansionContext context) {
        return new Predicate<ModelAttributeField>() {
            @Override
            public boolean apply(ModelAttributeField input) {
                return context.hasSeenType(input.getFieldType());
            }
        };
    }

    private Predicate<ModelAttributeField> simpleType() {
        return Predicates.and(new Predicate[]{Predicates.not(this.isCollection()), Predicates.not(this.isMap()), Predicates.or(new Predicate[]{this.belongsToJavaPackage(), this.isBaseType(), this.isEnum()})});
    }

    private Predicate<ModelAttributeField> isCollection() {
        return new Predicate<ModelAttributeField>() {
            @Override
            public boolean apply(ModelAttributeField input) {
                return Collections.isContainerType(input.getFieldType());
            }
        };
    }

    private Predicate<ModelAttributeField> isMap() {
        return new Predicate<ModelAttributeField>() {
            @Override
            public boolean apply(ModelAttributeField input) {
                return Maps.isMapType(input.getFieldType());
            }
        };
    }

    private Predicate<ModelAttributeField> isEnum() {
        return new Predicate<ModelAttributeField>() {
            @Override
            public boolean apply(ModelAttributeField input) {
                return ModelAttributeParameterExpanderExtension.this.enumTypeDeterminer.isEnum(input.getFieldType().getErasedType());
            }
        };
    }

    private Predicate<ModelAttributeField> belongsToJavaPackage() {
        return new Predicate<ModelAttributeField>() {
            @Override
            public boolean apply(ModelAttributeField input) {
                return ClassUtils.getPackageName(input.getFieldType().getErasedType()).startsWith("java.lang");
            }
        };
    }

    private Predicate<ModelAttributeField> isBaseType() {
        return new Predicate<ModelAttributeField>() {
            @Override
            public boolean apply(ModelAttributeField input) {
                return Types.isBaseType(input.getFieldType()) || input.getFieldType().isPrimitive();
            }
        };
    }

    private Function<ResolvedMethod, ModelAttributeField> toModelAttributeField(final Map<String, ResolvedField> fieldsByName, final Map<Method, PropertyDescriptor> propertyLookupByGetter, final AlternateTypeProvider alternateTypeProvider) {
        return new Function<ResolvedMethod, ModelAttributeField>() {
            @Override
            public ModelAttributeField apply(ResolvedMethod input) {
                String name = ((PropertyDescriptor) propertyLookupByGetter.get(input.getRawMember())).getName();
                return new ModelAttributeField(ModelAttributeParameterExpanderExtension.this.fieldType(alternateTypeProvider, input), name, input, (ResolvedMember) fieldsByName.get(name));
            }
        };
    }

    private Predicate<ResolvedMethod> onlyValidGetters(final Set<Method> methods) {
        return new Predicate<ResolvedMethod>() {
            @Override
            public boolean apply(ResolvedMethod input) {
                return methods.contains(input.getRawMember());
            }
        };
    }

    private String nestedParentName(String parentName, ModelAttributeField attribute) {
        String name = attribute.getName();
        ResolvedType fieldType = attribute.getFieldType();
        if (Collections.isContainerType(fieldType) && !Types.isBaseType(Collections.collectionElementType(fieldType))) {
            name = name + "[0]";
        }

        return Strings.isNullOrEmpty(parentName) ? name : String.format("%s.%s", parentName, name);
    }

    private ResolvedType fieldType(AlternateTypeProvider alternateTypeProvider, ResolvedMethod method) {
        return alternateTypeProvider.alternateFor(method.getType());
    }

    private Set<PropertyDescriptor> propertyDescriptors(Class<?> clazz) {
        try {
            return FluentIterable.from(this.getBeanInfo(clazz).getPropertyDescriptors()).toSet()
                    // 过滤掉@IgnoreSwagger2Parameter注解的属性
                    .stream()
                    .filter(propertyDescriptor -> {
                        Field field = FieldUtil.getDeclaredField(clazz, propertyDescriptor.getName());
                        if (field!=null) {
                            field.setAccessible(true);
                            IgnoreSwagger2Parameter ignoreSwaggerParameter = field.getDeclaredAnnotation(IgnoreSwagger2Parameter.class);
                            return ignoreSwaggerParameter == null;
                        }
                        return true;
                    })
                    .collect(Collectors.toSet());
        } catch (IntrospectionException var3) {
            LOG.warn(String.format("Failed to get bean properties on (%s)", clazz), var3);
            return Sets.newHashSet();
        }
    }

    private Map<Method, PropertyDescriptor> propertyDescriptorsByMethod(final Class<?> clazz, Set<PropertyDescriptor> propertyDescriptors) {
        return FluentIterable.from(propertyDescriptors).filter(new Predicate<PropertyDescriptor>() {
            @Override
            public boolean apply(PropertyDescriptor input) {
                return input.getReadMethod() != null && !clazz.isAssignableFrom(Collection.class) && !"isEmpty".equals(input.getReadMethod().getName());
            }
        }).uniqueIndex(new Function<PropertyDescriptor, Method>() {
            @Override
            public Method apply(PropertyDescriptor input) {
                return input.getReadMethod();
            }
        });
    }

    @VisibleForTesting
    BeanInfo getBeanInfo(Class<?> clazz) throws IntrospectionException {
        return Introspector.getBeanInfo(clazz);
    }

    static class ParameterTypeDeterminer {
        private ParameterTypeDeterminer() {
            throw new UnsupportedOperationException();
        }

        public static String determineScalarParameterType(Set<? extends MediaType> consumes, HttpMethod method) {
            String parameterType = "query";
            if (consumes.contains(MediaType.APPLICATION_FORM_URLENCODED) && method == HttpMethod.POST) {
                parameterType = "form";
            } else if (consumes.contains(MediaType.MULTIPART_FORM_DATA) && method == HttpMethod.POST) {
                parameterType = "formData";
            }

            return parameterType;
        }
    }
}
