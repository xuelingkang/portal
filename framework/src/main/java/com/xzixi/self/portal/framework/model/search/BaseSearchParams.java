package com.xzixi.self.portal.framework.model.search;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzixi.self.portal.framework.model.search.annotation.*;
import com.xzixi.self.portal.framework.util.BeanUtils;
import com.xzixi.self.portal.framework.util.ReflectUtil;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

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
public class BaseSearchParams<T> {

    private static final Pattern ASC_REG = Pattern.compile("\\s*[Aa][Ss][Cc]\\s*");

    /** 实体类参数 */
    private T entity;

    /** 当前页 */
    private Long current = 1L;

    /** 每页个数 */
    private Long size = 10L;

    /** 排序规则 */
    private String[] orderItems;

    /** 默认排序规则 */
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

    public void setDefaultOrderItems(String... defaultOrderItems) {
        this.defaultOrderItems = defaultOrderItems;
    }

    private static final Class<?>[] ANNOTATION_CLASSES
            = new Class<?>[]{Eq.class, Ne.class, Gt.class, Ge.class, Lt.class, Le.class, Like.class};

    /**
     * 创建QueryWrapper，忽略带条件注解的属性
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
        T instance = ReflectUtil.newInstance(cls);
        BeanUtils.copyPropertiesIgnoreNull(this.entity, instance, Arrays.stream(fields).filter(field ->
            findAnnotation(field) != null).map(Field::getName).toArray(String[]::new));
        return new QueryWrapper<>(instance);
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

            Condition condition = annotation.annotationType().getDeclaredAnnotation(Condition.class);
            if (condition == null) {
                continue;
            }
            ConditionType conditionType = condition.value();

            String column = ReflectUtil.invokeMethod(annotation, "value", new Class<?>[0]);
            if (StringUtils.isBlank(column)) {
                column = field.getName();
            }

            Object value = ReflectUtil.getProp(object, field);

            if (value == null && condition.ignoreNull()) {
                continue;
            }
            conditionType.parse(queryWrapper, column, value);
        }
    }
}