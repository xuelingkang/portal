package com.xzixi.self.portal.webapp.framework.model;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzixi.self.portal.webapp.framework.model.annotation.*;
import com.xzixi.self.portal.webapp.framework.util.ReflectUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "查询参数")
public class BaseSearchParams<T> {

    private static final Pattern ASC_REG = Pattern.compile("\\s*[Aa][Ss][Cc]\\s*");

    @ApiModelProperty(value = "实体类参数")
    private T entity;

    @ApiModelProperty(value = "当前页")
    private Long current = 1L;

    @ApiModelProperty(value = "每页个数")
    private Long size = 10L;

    @ApiModelProperty(value = "排序规则")
    private String[] orderItems;

    @ApiModelProperty(value = "默认排序规则")
    private String[] defaultOrderItems;

    /**
     * 生成分页查询参数
     *
     * @return Page&lt;T>
     */
    public Page<T> buildPageParams() {
        Page<T> page = new Page<>(this.current, this.size);
        String[] orderItems = this.orderItems;
        if (ArrayUtils.isEmpty(orderItems)) {
            orderItems = this.defaultOrderItems;
        }
        List<OrderItem> orderItemList = Arrays.stream(orderItems)
            // 按空白拆分
            .map(item -> item.split("\\s+"))
            // 过滤出长度为2的数组
            .filter(arr -> arr.length == 2)
            .map(arr -> {
                String column = arr[0];
                boolean asc = ASC_REG.matcher(arr[1]).matches();
                if (asc) {
                    return OrderItem.asc(column);
                }
                return OrderItem.desc(column);
            }).collect(Collectors.toList());
        page.addOrder(orderItemList);
        return page;
    }

    /**
     * 生成查询参数
     *
     * @return QueryWrapper&lt;T>
     */
    public QueryWrapper<T> buildQueryWrapper() {
        QueryWrapper<T> queryWrapper = newQueryWrapper();
        parseAnnotation(queryWrapper, this);
        parseAnnotation(queryWrapper, this.entity);
        return queryWrapper;
    }

    private static final Class<?>[] ANNOTATION_CLASSES
            = new Class<?>[]{Eq.class, Ge.class, Gt.class, Le.class, Lt.class, Like.class};

    /**
     * 创建QueryWrapper，忽略带Eq/Ge/Gt/Le/Lt/Like等注解的属性
     *
     * @return QueryWrapper
     */
    @SuppressWarnings("unchecked")
    private QueryWrapper<T> newQueryWrapper() {
        if (this.entity == null) {
            return new QueryWrapper<>();
        }
        Class<T> cls = (Class<T>) this.entity.getClass();
        Field[] fields = ReflectUtil.getDeclaredFields(cls);
        List<Field> fieldList = Arrays.stream(fields).filter(field -> findAnnotation(field) == null).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(fieldList)) {
            return new QueryWrapper<>();
        }
        T instance = ReflectUtil.newInstance(cls);
        fieldList.forEach(field -> ReflectUtil.setProp(instance, field, ReflectUtil.getProp(this.entity, field)));
        return new QueryWrapper<>(this.entity);
    }

    @SuppressWarnings("unchecked")
    private <A extends Annotation> Annotation findAnnotation(Field field) {
        field.setAccessible(true);
        for (Class<?> annotationClass : ANNOTATION_CLASSES) {
            A a = field.getDeclaredAnnotation((Class<A>) annotationClass);
            if (a != null) {
                return a;
            }
        }
        return null;
    }

    private void parseAnnotation(QueryWrapper<T> queryWrapper, Object object) {
        if (object == null) {
            return;
        }
        Field[] fields = ReflectUtil.getDeclaredFields(object.getClass());
        for (Field field : fields) {
            Annotation annotation = findAnnotation(field);
            if (annotation == null) {
                continue;
            }

            Object value = ReflectUtil.getProp(object, field);

            Class<?> annotationClass = annotation.getClass();
            String column = ReflectUtil.invokeMethod(annotation, "value", new Class<?>[0]);

            Parser parser = annotationClass.getDeclaredAnnotation(Parser.class);
            String methodName;
            Class<?>[] parameterTypes;
            if (parser == null) {
                methodName = annotationClass.getSimpleName().toLowerCase();
                parameterTypes = new Class<?>[]{String.class, Object.class};
            } else {
                methodName = parser.value();
                parameterTypes = parser.parameterTypes();
            }

            ReflectUtil.invokeMethod(queryWrapper, methodName, parameterTypes, column, value);
        }
    }
}
