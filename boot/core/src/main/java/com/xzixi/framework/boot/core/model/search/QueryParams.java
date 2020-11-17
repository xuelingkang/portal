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
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Consumer;

import static com.xzixi.framework.boot.core.util.TypeUtils.parseObject;

/**
 * 查询参数，<b>列名一律使用实体类的属性名</b><br>
 * 对mybatis-plus和elasticsearch等具体的实现解耦
 *
 * @author 薛凌康
 */
@Data
@NoArgsConstructor
public class QueryParams<T extends BaseModel> {

    /**
     * 分值
     */
    public static final String SCORE = "_score";

    private T model;

    private String[] columns;

    private Map<String, Object> eqMap = new LinkedHashMap<>();

    private Map<String, Object> neMap = new LinkedHashMap<>();

    private Map<String, Object> ltMap = new LinkedHashMap<>();

    private Map<String, Object> leMap = new LinkedHashMap<>();

    private Map<String, Object> gtMap = new LinkedHashMap<>();

    private Map<String, Object> geMap = new LinkedHashMap<>();

    private Map<String, Object> likeMap = new LinkedHashMap<>();

    private Map<String, Object> notLikeMap = new LinkedHashMap<>();

    private Map<String, Collection<?>> inMap = new LinkedHashMap<>();

    private Map<String, Collection<?>> notInMap = new LinkedHashMap<>();

    private Collection<String> nulls = new LinkedList<>();

    private Collection<String> notNulls = new LinkedList<>();

    private Collection<String> orders = new LinkedList<>();

    private Collection<QueryParams<T>> ands = new LinkedList<>();

    private Collection<QueryParams<T>> ors = new LinkedList<>();

    public QueryParams(T model) {
        this.model = model;
    }

    public QueryParams<T> columns(String... columns) {
        this.columns = columns;
        return this;
    }

    public QueryParams<T> eq(String name, Object value) {
        return eq(true, name, value);
    }

    public QueryParams<T> eq(boolean condition, String name, Object value) {
        if (condition) {
            this.eqMap.put(name, parseObject(value));
        }
        return this;
    }

    public QueryParams<T> ne(String name, Object value) {
        return ne(true, name, value);
    }

    public QueryParams<T> ne(boolean condition, String name, Object value) {
        if (condition) {
            this.neMap.put(name, parseObject(value));
        }
        return this;
    }

    public QueryParams<T> lt(String name, Object value) {
        return lt(true, name, value);
    }

    public QueryParams<T> lt(boolean condition, String name, Object value) {
        if (condition) {
            this.ltMap.put(name, parseObject(value));
        }
        return this;
    }

    public QueryParams<T> le(String name, Object value) {
        return le(true, name, value);
    }

    public QueryParams<T> le(boolean condition, String name, Object value) {
        if (condition) {
            this.leMap.put(name, parseObject(value));
        }
        return this;
    }

    public QueryParams<T> gt(String name, Object value) {
        return gt(true, name, value);
    }

    public QueryParams<T> gt(boolean condition, String name, Object value) {
        if (condition) {
            this.gtMap.put(name, parseObject(value));
        }
        return this;
    }

    public QueryParams<T> ge(String name, Object value) {
        return ge(true, name, value);
    }

    public QueryParams<T> ge(boolean condition, String name, Object value) {
        if (condition) {
            this.geMap.put(name, parseObject(value));
        }
        return this;
    }

    public QueryParams<T> like(String name, Object value) {
        return like(true, name, value);
    }

    public QueryParams<T> like(boolean condition, String name, Object value) {
        if (condition) {
            this.likeMap.put(name, parseObject(value));
        }
        return this;
    }

    public QueryParams<T> notLike(String name, Object value) {
        return notLike(true, name, value);
    }

    public QueryParams<T> notLike(boolean condition, String name, Object value) {
        if (condition) {
            this.notLikeMap.put(name, parseObject(value));
        }
        return this;
    }

    public QueryParams<T> in(String name, Collection<?> value) {
        return in(true, name, value);
    }

    public QueryParams<T> in(boolean condition, String name, Collection<?> collection) {
        if (condition) {
            this.inMap.put(name, collection);
        }
        return this;
    }

    public QueryParams<T> notIn(String name, Collection<?> value) {
        return notIn(true, name, value);
    }

    public QueryParams<T> notIn(boolean condition, String name, Collection<?> collection) {
        if (condition) {
            this.notInMap.put(name, collection);
        }
        return this;
    }

    public QueryParams<T> isNull(String name) {
        return isNull(true, name);
    }

    public QueryParams<T> isNull(boolean condition, String name) {
        if (condition) {
            this.nulls.add(name);
        }
        return this;
    }

    public QueryParams<T> isNotNull(String name) {
        return isNotNull(true, name);
    }

    public QueryParams<T> isNotNull(boolean condition, String name) {
        if (condition) {
            this.notNulls.add(name);
        }
        return this;
    }

    public QueryParams<T> orderBy(String order) {
        this.orders.add(order);
        return this;
    }

    public QueryParams<T> and(Consumer<QueryParams<T>> consumer) {
        return and(true, consumer);
    }

    public QueryParams<T> and(boolean condition, Consumer<QueryParams<T>> consumer) {
        if (condition) {
            QueryParams<T> params = new QueryParams<>();
            this.ands.add(params);
            consumer.accept(params);
        }
        return this;
    }

    public QueryParams<T> or(Consumer<QueryParams<T>> consumer) {
        return or(true, consumer);
    }

    public QueryParams<T> or(boolean condition, Consumer<QueryParams<T>> consumer) {
        if (condition) {
            QueryParams<T> params = new QueryParams<>();
            this.ors.add(params);
            consumer.accept(params);
        }
        return this;
    }
}
