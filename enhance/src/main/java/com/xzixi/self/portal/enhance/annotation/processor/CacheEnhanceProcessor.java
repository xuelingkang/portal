package com.xzixi.self.portal.enhance.annotation.processor;

import com.sun.tools.javac.code.BoundKind;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import com.xzixi.self.portal.enhance.annotation.CacheEnhance;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

/**
 * @author 薛凌康
 */
@SupportedAnnotationTypes("com.xzixi.self.portal.enhance.annotation.CacheEnhance")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class CacheEnhanceProcessor extends AbstractBaseProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // 遍历所有@CacheEnhance标注的类
        for (Element element: roundEnv.getElementsAnnotatedWith(CacheEnhance.class)) {
            CacheEnhance cacheEnhance = element.getAnnotation(CacheEnhance.class);

            // 类定义
            JCTree.JCClassDecl classDecl = (JCTree.JCClassDecl) elements.getTree(element);

            // 当前类对应的实体类的类名
            String modelClassName = ((JCTree.JCTypeApply) classDecl.extending.getTree()).arguments.get(1).type.toString();

            // 修改类，追加方法
            if (cacheEnhance.getById()) {
                classDecl.defs = classDecl.defs.append(getByIdDecl(modelClassName));
            }
            if (cacheEnhance.listByIds()) {
                classDecl.defs = classDecl.defs.append(listByIdsDecl(modelClassName));
            }
            if (cacheEnhance.getOne()) {
                classDecl.defs = classDecl.defs.append(getOneDecl(modelClassName));
            }
            if (cacheEnhance.list()) {
                classDecl.defs = classDecl.defs.append(listDecl(modelClassName));
            }
            if (cacheEnhance.listByMap()) {
                classDecl.defs = classDecl.defs.append(listByMapDecl(modelClassName));
            }
            if (cacheEnhance.page()) {
                classDecl.defs = classDecl.defs.append(pageDecl(modelClassName));
            }
            if (cacheEnhance.count()) {
                classDecl.defs = classDecl.defs.append(countDecl(modelClassName));
            }
            if (cacheEnhance.updateById()) {
                classDecl.defs = classDecl.defs.append(updateByIdDecl(modelClassName));
            }
            if (cacheEnhance.updateBatchById()) {
                classDecl.defs = classDecl.defs.append(updateBatchByIdDecl(modelClassName));
            }
            if (cacheEnhance.save()) {
                classDecl.defs = classDecl.defs.append(saveDecl(modelClassName));
            }
            if (cacheEnhance.saveBatch()) {
                classDecl.defs = classDecl.defs.append(saveBatchDecl(modelClassName));
            }
            if (cacheEnhance.saveOrUpdate()) {
                classDecl.defs = classDecl.defs.append(saveOrUpdateDecl(modelClassName));
            }
            if (cacheEnhance.saveOrUpdateBatch()) {
                classDecl.defs = classDecl.defs.append(saveOrUpdateBatchDecl(modelClassName));
            }
            if (cacheEnhance.removeById()) {
                classDecl.defs = classDecl.defs.append(removeByIdDecl());
            }
            if (cacheEnhance.removeByIds()) {
                classDecl.defs = classDecl.defs.append(removeByIdsDecl());
            }
        }
        // 返回true，其他processor不再处理这个注解
        return true;
    }

    private static final String OVERRIDE = "java.lang.Override";
    private static final String TRANSACTIONAL = "org.springframework.transaction.annotation.Transactional";
    private static final String CACHE_ABLE = "org.springframework.cache.annotation.Cacheable";
    private static final String CACHE_EVICT = "org.springframework.cache.annotation.CacheEvict";
    private static final String CACHING = "org.springframework.cache.annotation.Caching";

    private static final String EXCEPTION = "java.lang.Exception.class";
    private static final String STRING = "java.lang.String";
    private static final String OBJECT = "java.lang.Object";
    private static final String SERIALIZABLE = "java.io.Serializable";
    private static final String COLLECTION = "java.util.Collection";
    private static final String LIST = "java.util.List";
    private static final String MAP = "java.util.Map";
    private static final String I_PAGE = "com.baomidou.mybatisplus.core.metadata.IPage";
    private static final String WRAPPER = "com.baomidou.mybatisplus.core.conditions.Wrapper";

    private static final String ROLLBACK_FOR = "rollbackFor";
    private static final String CACHE_NAMES = "cacheNames";
    private static final String KEY_GENERATOR = "keyGenerator";
    private static final String ALL_ENTRIES = "allEntries";
    private static final String EVICT = "evict";

    private static final String BASE_CACHE_NAME = "BASE_CACHE_NAME";
    private static final String CASUAL_CACHE_NAME = "CASUAL_CACHE_NAME";

    private static final String DEFAULT_BASE_KEY_GENERATOR = "defaultBaseKeyGenerator";
    private static final String DEFAULT_CASUAL_KEY_GENERATOR = "defaultCasualKeyGenerator";
    private static final String DEFAULT_EVICT_BY_ID_KEY_GENERATOR = "defaultEvictByIdKeyGenerator";
    private static final String DEFAULT_EVICT_BY_IDS_KEY_GENERATOR = "defaultEvictByIdsKeyGenerator";
    private static final String DEFAULT_EVICT_BY_ENTITY_KEY_GENERATOR = "defaultEvictByEntityKeyGenerator";
    private static final String DEFAULT_EVICT_BY_ENTITIES_KEY_GENERATOR = "defaultEvictByEntitiesKeyGenerator";

    /**
     * getById
     */
    private JCTree.JCMethodDecl getByIdDecl(String modelClassName) {
        final String method = "getById";
        final String superMethod = "super.getById";
        final String id = "id";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), baseCache());
        // 访问修饰词和注解列表
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC, annotationList);
        // 方法名
        Name name = getNameFromString(method);
        // 返回值类型
        JCTree.JCExpression returnType = memberAccess(modelClassName);
        // 参数列表
        List<JCTree.JCVariableDecl> parameters = List.of(typeParamDecl(id, SERIALIZABLE));
        // 方法体
        JCTree.JCBlock block = treeMaker.Block(0, List.of(superReturn(superMethod, id)));
        return treeMaker.MethodDef(modifiers, name, returnType, List.nil(), parameters, List.nil(), block, null);
    }

    /**
     * listByIds
     */
    public JCTree.JCMethodDecl listByIdsDecl(String modelClassName) {
        final String method = "listByIds";
        final String superMethod = "super.listByIds";
        final String idList = "idList";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), baseCache());
        // 访问修饰词和注解列表
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC, annotationList);
        // 方法名
        Name name = getNameFromString(method);
        // 返回值类型
        JCTree.JCExpression returnType = collectionType(modelClassName);
        // 参数列表
        List<JCTree.JCVariableDecl> parameters = List.of(extendsWildCollectionParamDecl(idList, SERIALIZABLE));
        // 方法体
        JCTree.JCBlock block = treeMaker.Block(0, List.of(superReturn(superMethod, idList)));
        return treeMaker.MethodDef(modifiers, name, returnType, List.nil(), parameters, List.nil(), block, null);
    }

    /**
     * getOne
     */
    public JCTree.JCMethodDecl getOneDecl(String modelClassName) {
        final String method = "getOne";
        final String superMethod = "super.getOne";
        final String queryWrapper = "queryWrapper";
        final String throwEx = "throwEx";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), casualCache());
        // 访问修饰词和注解列表
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC, annotationList);
        // 方法名
        Name name = getNameFromString(method);
        // 返回值类型
        JCTree.JCExpression returnType = memberAccess(modelClassName);
        // 参数列表
        List<JCTree.JCVariableDecl> parameters = List.of(wrapperParamDecl(queryWrapper, modelClassName), booleanParamDecl(throwEx));
        // 方法体
        JCTree.JCBlock block = treeMaker.Block(0, List.of(superReturn(superMethod, queryWrapper, throwEx)));
        return treeMaker.MethodDef(modifiers, name, returnType, List.nil(), parameters, List.nil(), block, null);
    }

    /**
     * list
     */
    public JCTree.JCMethodDecl listDecl(String modelClassName) {
        final String method = "list";
        final String superMethod = "super.list";
        final String queryWrapper = "queryWrapper";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), casualCache());
        // 访问修饰词和注解列表
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC, annotationList);
        // 方法名
        Name name = getNameFromString(method);
        // 返回值类型
        JCTree.JCExpression returnType = listType(modelClassName);
        // 参数列表
        List<JCTree.JCVariableDecl> parameters = List.of(wrapperParamDecl(queryWrapper, modelClassName));
        // 方法体
        JCTree.JCBlock block = treeMaker.Block(0, List.of(superReturn(superMethod, queryWrapper)));
        return treeMaker.MethodDef(modifiers, name, returnType, List.nil(), parameters, List.nil(), block, null);
    }

    /**
     * listByMap
     */
    public JCTree.JCMethodDecl listByMapDecl(String modelClassName) {
        final String method = "listByMap";
        final String superMethod = "super.listByMap";
        final String columnMap = "columnMap";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), casualCache());
        // 访问修饰词和注解列表
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC, annotationList);
        // 方法名
        Name name = getNameFromString(method);
        // 返回值类型
        JCTree.JCExpression returnType = collectionType(modelClassName);
        // 参数列表
        List<JCTree.JCVariableDecl> parameters = List.of(mapParamDecl(columnMap, STRING, OBJECT));
        // 方法体
        JCTree.JCBlock block = treeMaker.Block(0, List.of(superReturn(superMethod, columnMap)));
        return treeMaker.MethodDef(modifiers, name, returnType, List.nil(), parameters, List.nil(), block, null);
    }

    /**
     * page
     */
    public JCTree.JCMethodDecl pageDecl(String modelClassName) {
        final String method = "page";
        final String superMethod = "super.page";
        final String page = "page";
        final String queryWrapper = "queryWrapper";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), casualCache());
        // 访问修饰词和注解列表
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC, annotationList);
        // 方法名
        Name name = getNameFromString(method);
        // 返回值类型
        JCTree.JCExpression returnType = iPageType(modelClassName);
        // 参数列表
        List<JCTree.JCVariableDecl> parameters = List.of(iPageParamDecl(page, modelClassName), wrapperParamDecl(queryWrapper, modelClassName));
        // 方法体
        JCTree.JCBlock block = treeMaker.Block(0, List.of(superReturn(superMethod, page, queryWrapper)));
        return treeMaker.MethodDef(modifiers, name, returnType, List.nil(), parameters, List.nil(), block, null);
    }

    /**
     * count
     */
    private JCTree.JCMethodDecl countDecl(String modelClassName) {
        final String method = "count";
        final String superMethod = "super.count";
        final String queryWrapper = "queryWrapper";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), casualCache());
        // 访问修饰词和注解列表
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC, annotationList);
        // 方法名
        Name name = getNameFromString(method);
        // 返回值类型
        JCTree.JCExpression returnType = treeMaker.TypeIdent(TypeTag.INT);
        // 参数列表
        List<JCTree.JCVariableDecl> parameters = List.of(wrapperParamDecl(queryWrapper, modelClassName));
        // 方法体
        JCTree.JCBlock block = treeMaker.Block(0, List.of(superReturn(superMethod, queryWrapper)));
        return treeMaker.MethodDef(modifiers, name, returnType, List.nil(), parameters, List.nil(), block, null);
    }

    /**
     * updateById
     */
    private JCTree.JCMethodDecl updateByIdDecl(String modelClassName) {
        final String method = "updateById";
        final String superMethod = "super.updateById";
        final String entity = "entity";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), transactional(), evicts(byEntityEvict(), casualEvict()));
        // 访问修饰词和注解列表
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC, annotationList);
        // 方法名
        Name name = getNameFromString(method);
        // 返回值类型
        JCTree.JCExpression returnType = treeMaker.TypeIdent(TypeTag.BOOLEAN);
        // 参数列表
        List<JCTree.JCVariableDecl> parameters = List.of(typeParamDecl(entity, modelClassName));
        // 方法体
        JCTree.JCBlock block = treeMaker.Block(0, List.of(superReturn(superMethod, entity)));
        return treeMaker.MethodDef(modifiers, name, returnType, List.nil(), parameters, List.nil(), block, null);
    }

    /**
     * updateBatchById
     */
    private JCTree.JCMethodDecl updateBatchByIdDecl(String modelClassName) {
        final String method = "updateBatchById";
        final String superMethod = "super.updateBatchById";
        final String entityList = "entityList";
        final String batchSize = "batchSize";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), transactional(), evicts(byEntitiesEvict(), casualEvict()));
        // 访问修饰词和注解列表
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC, annotationList);
        // 方法名
        Name name = getNameFromString(method);
        // 返回值类型
        JCTree.JCExpression returnType = treeMaker.TypeIdent(TypeTag.BOOLEAN);
        // 参数列表
        List<JCTree.JCVariableDecl> parameters = List.of(collectionParamDecl(entityList, modelClassName), intParamDecl(batchSize));
        // 方法体
        JCTree.JCBlock block = treeMaker.Block(0, List.of(superReturn(superMethod, entityList, batchSize)));
        return treeMaker.MethodDef(modifiers, name, returnType, List.nil(), parameters, List.nil(), block, null);
    }

    /**
     * save
     */
    private JCTree.JCMethodDecl saveDecl(String modelClassName) {
        final String method = "save";
        final String superMethod = "super.save";
        final String entity = "entity";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), transactional(), casualEvict());
        // 访问修饰词和注解列表
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC, annotationList);
        // 方法名
        Name name = getNameFromString(method);
        // 返回值类型
        JCTree.JCExpression returnType = treeMaker.TypeIdent(TypeTag.BOOLEAN);
        // 参数列表
        List<JCTree.JCVariableDecl> parameters = List.of(typeParamDecl(entity, modelClassName));
        // 方法体
        JCTree.JCBlock block = treeMaker.Block(0, List.of(superReturn(superMethod, entity)));
        return treeMaker.MethodDef(modifiers, name, returnType, List.nil(), parameters, List.nil(), block, null);
    }

    /**
     * saveBatch
     */
    private JCTree.JCMethodDecl saveBatchDecl(String modelClassName) {
        final String method = "saveBatch";
        final String superMethod = "super.saveBatch";
        final String entityList = "entityList";
        final String batchSize = "batchSize";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), transactional(), casualEvict());
        // 访问修饰词和注解列表
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC, annotationList);
        // 方法名
        Name name = getNameFromString(method);
        // 返回值类型
        JCTree.JCExpression returnType = treeMaker.TypeIdent(TypeTag.BOOLEAN);
        // 参数列表
        List<JCTree.JCVariableDecl> parameters = List.of(collectionParamDecl(entityList, modelClassName), intParamDecl(batchSize));
        // 方法体
        JCTree.JCBlock block = treeMaker.Block(0, List.of(superReturn(superMethod, entityList, batchSize)));
        return treeMaker.MethodDef(modifiers, name, returnType, List.nil(), parameters, List.nil(), block, null);
    }

    /**
     * saveOrUpdate
     */
    private JCTree.JCMethodDecl saveOrUpdateDecl(String modelClassName) {
        final String method = "saveOrUpdate";
        final String superMethod = "super.saveOrUpdate";
        final String entity = "entity";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), transactional(), evicts(byEntityEvict(), casualEvict()));
        // 访问修饰词和注解列表
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC, annotationList);
        // 方法名
        Name name = getNameFromString(method);
        // 返回值类型
        JCTree.JCExpression returnType = treeMaker.TypeIdent(TypeTag.BOOLEAN);
        // 参数列表
        List<JCTree.JCVariableDecl> parameters = List.of(typeParamDecl(entity, modelClassName));
        // 方法体
        JCTree.JCBlock block = treeMaker.Block(0, List.of(superReturn(superMethod, entity)));
        return treeMaker.MethodDef(modifiers, name, returnType, List.nil(), parameters, List.nil(), block, null);
    }

    /**
     * saveOrUpdateBatch
     */
    private JCTree.JCMethodDecl saveOrUpdateBatchDecl(String modelClassName) {
        final String method = "saveOrUpdateBatch";
        final String superMethod = "super.saveOrUpdateBatch";
        final String entityList = "entityList";
        final String batchSize = "batchSize";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), transactional(), evicts(byEntitiesEvict(), casualEvict()));
        // 访问修饰词和注解列表
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC, annotationList);
        // 方法名
        Name name = getNameFromString(method);
        // 返回值类型
        JCTree.JCExpression returnType = treeMaker.TypeIdent(TypeTag.BOOLEAN);
        // 参数列表
        List<JCTree.JCVariableDecl> parameters = List.of(collectionParamDecl(entityList, modelClassName), intParamDecl(batchSize));
        // 方法体
        JCTree.JCBlock block = treeMaker.Block(0, List.of(superReturn(superMethod, entityList, batchSize)));
        return treeMaker.MethodDef(modifiers, name, returnType, List.nil(), parameters, List.nil(), block, null);
    }

    /**
     * removeById
     */
    private JCTree.JCMethodDecl removeByIdDecl() {
        final String method = "removeById";
        final String superMethod = "super.removeById";
        final String id = "id";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), transactional(), evicts(byIdEvict(), casualEvict()));
        // 访问修饰词和注解列表
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC, annotationList);
        // 方法名
        Name name = getNameFromString(method);
        // 返回值类型
        JCTree.JCExpression returnType = treeMaker.TypeIdent(TypeTag.BOOLEAN);
        // 参数列表
        List<JCTree.JCVariableDecl> parameters = List.of(typeParamDecl(id, SERIALIZABLE));
        // 方法体
        JCTree.JCBlock block = treeMaker.Block(0, List.of(superReturn(superMethod, id)));
        return treeMaker.MethodDef(modifiers, name, returnType, List.nil(), parameters, List.nil(), block, null);
    }

    /**
     * removeByIds
     */
    private JCTree.JCMethodDecl removeByIdsDecl() {
        final String method = "removeByIds";
        final String superMethod = "super.removeByIds";
        final String idList = "idList";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), transactional(), evicts(byIdsEvict(), casualEvict()));
        // 访问修饰词和注解列表
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC, annotationList);
        // 方法名
        Name name = getNameFromString(method);
        // 返回值类型
        JCTree.JCExpression returnType = treeMaker.TypeIdent(TypeTag.BOOLEAN);
        // 参数列表
        List<JCTree.JCVariableDecl> parameters = List.of(extendsWildCollectionParamDecl(idList, SERIALIZABLE));
        // 方法体
        JCTree.JCBlock block = treeMaker.Block(0, List.of(superReturn(superMethod, idList)));
        return treeMaker.MethodDef(modifiers, name, returnType, List.nil(), parameters, List.nil(), block, null);
    }

    /**
     * Collection&lt;innerClassName>
     */
    private JCTree.JCTypeApply collectionType(String innerClassName) {
        return treeMaker.TypeApply(memberAccess(COLLECTION), List.of(memberAccess(innerClassName)));
    }

    /**
     * Collection&lt;innerClassName>
     */
    private JCTree.JCTypeApply extendsWildCollectionType(String innerClassName) {
        return treeMaker.TypeApply(memberAccess(COLLECTION),
                List.of(treeMaker.Wildcard(treeMaker.TypeBoundKind(BoundKind.EXTENDS), memberAccess(innerClassName))));
    }

    /**
     * List&lt;innerClassName>
     */
    private JCTree.JCTypeApply listType(String innerClassName) {
        return treeMaker.TypeApply(memberAccess(LIST), List.of(memberAccess(innerClassName)));
    }

    /**
     * Wrapper&lt;innerClassName>
     */
    private JCTree.JCTypeApply wrapperType(String innerClassName) {
        return treeMaker.TypeApply(memberAccess(WRAPPER), List.of(memberAccess(innerClassName)));
    }

    /**
     * Map&lt;keyClassName, valueClassName>
     */
    private JCTree.JCTypeApply mapType(String keyClassName, String valueClassName) {
        return treeMaker.TypeApply(memberAccess(MAP), List.of(memberAccess(keyClassName), memberAccess(valueClassName)));
    }

    /**
     * IPage&lt;innerClassName>
     */
    private JCTree.JCTypeApply iPageType(String innerClassName) {
        return treeMaker.TypeApply(memberAccess(I_PAGE), List.of(memberAccess(innerClassName)));
    }

    /**
     * boolean
     */
    private JCTree.JCVariableDecl booleanParamDecl(String name) {
        return treeMaker.VarDef(treeMaker.Modifiers(Flags.PARAMETER), getNameFromString(name), treeMaker.TypeIdent(TypeTag.BOOLEAN), null);
    }

    /**
     * int
     */
    private JCTree.JCVariableDecl intParamDecl(String name) {
        return treeMaker.VarDef(treeMaker.Modifiers(Flags.PARAMETER), getNameFromString(name), treeMaker.TypeIdent(TypeTag.INT), null);
    }

    /**
     * 包装类型
     */
    private JCTree.JCVariableDecl typeParamDecl(String name, String modelClassName) {
        return treeMaker.VarDef(treeMaker.Modifiers(Flags.PARAMETER), getNameFromString(name), memberAccess(modelClassName), null);
    }

    private JCTree.JCVariableDecl collectionParamDecl(String name, String innerClassName) {
        return treeMaker.VarDef(treeMaker.Modifiers(Flags.PARAMETER), getNameFromString(name), collectionType(innerClassName), null);
    }

    /**
     * Collection&lt;? extends innerClassName>
     */
    private JCTree.JCVariableDecl extendsWildCollectionParamDecl(String name, String innerClassName) {
        return treeMaker.VarDef(treeMaker.Modifiers(Flags.PARAMETER), getNameFromString(name), extendsWildCollectionType(innerClassName), null);
    }

    /**
     * Wrapper&lt;innerClassName>
     */
    private JCTree.JCVariableDecl wrapperParamDecl(String name, String innerClassName) {
        return treeMaker.VarDef(treeMaker.Modifiers(Flags.PARAMETER), getNameFromString(name), wrapperType(innerClassName), null);
    }

    /**
     * Map&lt;keyClassName, valueClassName>
     */
    private JCTree.JCVariableDecl mapParamDecl(String name, String keyClassName, String valueClassName) {
        return treeMaker.VarDef(treeMaker.Modifiers(Flags.PARAMETER), getNameFromString(name), mapType(keyClassName, valueClassName), null);
    }

    /**
     * IPage&lt;innerClassName>
     */
    private JCTree.JCVariableDecl iPageParamDecl(String name, String innerClassName) {
        return treeMaker.VarDef(treeMaker.Modifiers(Flags.PARAMETER), getNameFromString(name), iPageType(innerClassName), null);
    }

    /**
     * 调用父类方法并return
     */
    private JCTree.JCReturn superReturn(String method, String... parameters) {
        List<JCTree.JCExpression> expressions = List.nil();
        for (String parameter : parameters) {
            expressions = expressions.append(memberAccess(parameter));
        }
        return treeMaker.Return(treeMaker.Apply(List.nil(), memberAccess(method), expressions));
    }

    /**
     * {@code @Override}
     */
    private JCTree.JCAnnotation override() {
        return treeMaker.Annotation(memberAccess(OVERRIDE), List.nil());
    }

    /**
     * {@code @Transactional(rollbackFor = Exception.class)}
     */
    private JCTree.JCAnnotation transactional() {
        return treeMaker.Annotation(memberAccess(TRANSACTIONAL), List.of(treeMaker.Assign(memberAccess(ROLLBACK_FOR), memberAccess(EXCEPTION))));
    }

    /**
     * {@code @Cacheable(cacheNames=BASE_CACHE_NAME, keyGenerator="defaultBaseKeyGenerator")}
     */
    private JCTree.JCAnnotation baseCache() {
        return treeMaker.Annotation(memberAccess(CACHE_ABLE),
                List.of(treeMaker.Assign(memberAccess(CACHE_NAMES), memberAccess(BASE_CACHE_NAME)),
                        treeMaker.Assign(memberAccess(KEY_GENERATOR), treeMaker.Literal(DEFAULT_BASE_KEY_GENERATOR))));
    }

    /**
     * {@code @Cacheable(cacheNames=CASUAL_CACHE_NAME, keyGenerator="defaultCasualKeyGenerator")}
     */
    private JCTree.JCAnnotation casualCache() {
        return treeMaker.Annotation(memberAccess(CACHE_ABLE),
                List.of(treeMaker.Assign(memberAccess(CACHE_NAMES), memberAccess(CASUAL_CACHE_NAME)),
                        treeMaker.Assign(memberAccess(KEY_GENERATOR), treeMaker.Literal(DEFAULT_CASUAL_KEY_GENERATOR))));
    }

    /**
     * {@code @CacheEvict(cacheNames=BASE_CACHE_NAME, keyGenerator="defaultEvictByIdKeyGenerator")}
     */
    private JCTree.JCAnnotation byIdEvict() {
        return treeMaker.Annotation(memberAccess(CACHE_EVICT),
                List.of(treeMaker.Assign(memberAccess(CACHE_NAMES), memberAccess(BASE_CACHE_NAME)),
                        treeMaker.Assign(memberAccess(KEY_GENERATOR), treeMaker.Literal(DEFAULT_EVICT_BY_ID_KEY_GENERATOR))));
    }

    /**
     * {@code @CacheEvict(cacheNames=BASE_CACHE_NAME, keyGenerator="defaultEvictByIdsKeyGenerator")}
     */
    private JCTree.JCAnnotation byIdsEvict() {
        return treeMaker.Annotation(memberAccess(CACHE_EVICT),
                List.of(treeMaker.Assign(memberAccess(CACHE_NAMES), memberAccess(BASE_CACHE_NAME)),
                        treeMaker.Assign(memberAccess(KEY_GENERATOR), treeMaker.Literal(DEFAULT_EVICT_BY_IDS_KEY_GENERATOR))));
    }

    /**
     * {@code @CacheEvict(cacheNames=BASE_CACHE_NAME, keyGenerator="defaultEvictByEntityKeyGenerator")}
     */
    private JCTree.JCAnnotation byEntityEvict() {
        return treeMaker.Annotation(memberAccess(CACHE_EVICT),
                List.of(treeMaker.Assign(memberAccess(CACHE_NAMES), memberAccess(BASE_CACHE_NAME)),
                        treeMaker.Assign(memberAccess(KEY_GENERATOR), treeMaker.Literal(DEFAULT_EVICT_BY_ENTITY_KEY_GENERATOR))));
    }

    /**
     * {@code @CacheEvict(cacheNames=BASE_CACHE_NAME, keyGenerator="defaultEvictByEntitiesKeyGenerator")}
     */
    private JCTree.JCAnnotation byEntitiesEvict() {
        return treeMaker.Annotation(memberAccess(CACHE_EVICT),
                List.of(treeMaker.Assign(memberAccess(CACHE_NAMES), memberAccess(BASE_CACHE_NAME)),
                        treeMaker.Assign(memberAccess(KEY_GENERATOR), treeMaker.Literal(DEFAULT_EVICT_BY_ENTITIES_KEY_GENERATOR))));
    }

    /**
     * {@code @CacheEvict(cacheNames=CASUAL_CACHE_NAME, allEntries=true)}
     */
    private JCTree.JCAnnotation casualEvict() {
        return treeMaker.Annotation(memberAccess(CACHE_EVICT),
                List.of(treeMaker.Assign(memberAccess(CACHE_NAMES), memberAccess(CASUAL_CACHE_NAME)),
                        treeMaker.Assign(memberAccess(ALL_ENTRIES), treeMaker.Literal(true))));
    }

    /**
     * {@code @Caching(evict={})}
     */
    private JCTree.JCAnnotation evicts(JCTree.JCAnnotation... cacheEvicts) {
        if (cacheEvicts.length == 0) {
            messager.printMessage(Diagnostic.Kind.ERROR, "cacheEvicts不能为空！");
        }
        List<JCTree.JCExpression> evicts = List.nil();
        for (JCTree.JCAnnotation cacheEvict : cacheEvicts) {
            evicts = evicts.append(cacheEvict);
        }
        JCTree.JCExpression rhs = treeMaker.NewArray(null, List.nil(), evicts);
        return treeMaker.Annotation(memberAccess(CACHING),
                List.of(treeMaker.Assign(memberAccess(EVICT), rhs)));
    }
}
