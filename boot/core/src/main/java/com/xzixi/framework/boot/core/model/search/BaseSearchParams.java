/*
 * The spring-based xzixi framework simplifies development.
 *
 * Copyright (C) 2020  xuelingkang@163.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.xzixi.framework.boot.core.model.search;

import com.xzixi.framework.boot.core.model.BaseModel;
import com.xzixi.framework.boot.core.util.BeanUtils;
import com.xzixi.framework.boot.core.util.OrderUtils;
import com.xzixi.framework.boot.core.util.ReflectUtils;
import com.xzixi.framework.boot.core.model.search.annotation.*;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;

/**
 * @author 薛凌康
 */
@Data
public class BaseSearchParams<T extends BaseModel> {

    /**
     * 实体类参数
     */
    private T model;

    /**
     * 当前页
     */
    private Long current = 1L;

    /**
     * 每页个数
     */
    private Long size = 10L;

    /**
     * 排序规则
     */
    private String[] orders;

    /**
     * 默认排序规则
     */
    private String[] defaultOrders;

    @SuppressWarnings("unchecked")
    public BaseSearchParams() {
        // 获取泛型类型
        Class<T> tClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        // 初始化entity，防止空指针异常
        this.model = ReflectUtils.newInstance(tClass);
    }

    /**
     * 生成分页查询参数
     *
     * @return Page&lt;T>
     */
    public Pagination<T> buildPagination() {
        Pagination<T> pagination = new Pagination<>(current, size);
        String[] orders = null;
        if (this.orders != null && ArrayUtils.isNotEmpty(this.orders)) {
            orders = Arrays.stream(this.orders)
                    .filter(order -> OrderUtils.parse(order) != null).toArray(String[]::new);
        }
        if (orders != null && ArrayUtils.isNotEmpty(orders)) {
            pagination.setOrders(orders);
        } else {
            pagination.setOrders(defaultOrders);
        }
        return pagination;
    }

    /**
     * 生成查询参数
     *
     * @return QueryWrapper&lt;T>
     */
    public QueryParams<T> buildQueryParams() {
        QueryParams<T> queryParams = newQueryParams();
        parseAnnotation(queryParams, this);
        parseAnnotation(queryParams, this.model);
        return queryParams;
    }

    public void setDefaultOrders(String... defaultOrders) {
        this.defaultOrders = defaultOrders;
    }

    private static final Class<?>[] ANNOTATION_CLASSES
            = new Class<?>[]{Eq.class, Ne.class, Gt.class, Ge.class, Lt.class, Le.class, Like.class, NotLike.class, In.class, NotIn.class};

    /**
     * 创建QueryWrapper，忽略带条件注解的属性
     *
     * @return QueryWrapper
     */
    @SuppressWarnings("unchecked")
    private QueryParams<T> newQueryParams() {
        if (this.model == null) {
            return new QueryParams<>();
        }
        Class<T> cls = (Class<T>) this.model.getClass();
        Field[] fields = ReflectUtils.getDeclaredFields(cls);
        T instance = ReflectUtils.newInstance(cls);
        BeanUtils.copyPropertiesIgnoreNull(this.model, instance, Arrays.stream(fields).filter(field ->
                findAnnotation(field) != null).map(Field::getName).toArray(String[]::new));
        return new QueryParams<>(instance);
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

    private void parseAnnotation(QueryParams<T> queryParams, Object object) {
        if (object == null) {
            return;
        }
        Field[] fields = ReflectUtils.getDeclaredFields(object.getClass());
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

            String column = ReflectUtils.invokeMethod(annotation, "value", new Class<?>[0]);
            if (StringUtils.isBlank(column)) {
                column = field.getName();
            }

            Object value = ReflectUtils.getProp(object, field);

            if (value == null && condition.ignoreNull()) {
                continue;
            }
            conditionType.parse(queryParams, column, value);
        }
    }
}
