package com.xzixi.self.portal.framework.model.search;

import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;

/**
 * @author 薛凌康
 */
public enum ConditionType {

    /**
     * 等于
     */
    EQ() {
        @Override
        public void parse(QueryParams<?> params, String column, Object value) {
            if (value == null) {
                params.isNull(column);
                return;
            }
            params.eq(column, value);
        }
    },
    /**
     * 不等于
     */
    NE() {
        @Override
        public void parse(QueryParams<?> params, String column, Object value) {
            if (value == null) {
                params.isNotNull(column);
                return;
            }
            params.ne(column, value);
        }
    },
    /**
     * 大于
     */
    GT() {
        @Override
        public void parse(QueryParams<?> params, String column, Object value) {
            if (value == null) {
                return;
            }
            params.gt(column, value);
        }
    },
    /**
     * 大于等于
     */
    GE() {
        @Override
        public void parse(QueryParams<?> params, String column, Object value) {
            if (value == null) {
                return;
            }
            params.ge(column, value);
        }
    },
    /**
     * 小于
     */
    LT() {
        @Override
        public void parse(QueryParams<?> params, String column, Object value) {
            if (value == null) {
                return;
            }
            params.lt(column, value);
        }
    },
    /**
     * 小于等于
     */
    LE() {
        @Override
        public void parse(QueryParams<?> params, String column, Object value) {
            if (value == null) {
                return;
            }
            params.le(column, value);
        }
    },
    /**
     * 模糊搜索
     */
    LIKE() {
        @Override
        public void parse(QueryParams<?> params, String column, Object value) {
            if (value == null) {
                return;
            }
            params.like(column, value);
        }
    },
    NOT_LIKE() {
        @Override
        public void parse(QueryParams<?> params, String column, Object value) {
            if (value == null) {
                return;
            }
            params.notLike(column, value);
        }
    },
    IN() {
        @Override
        public void parse(QueryParams<?> params, String column, Object value) {
            Collection<?> collection = (Collection<?>) value;
            if (CollectionUtils.isEmpty(collection)) {
                return;
            }
            params.in(column, collection);
        }
    },
    NOT_IN() {
        @Override
        public void parse(QueryParams<?> params, String column, Object value) {
            Collection<?> collection = (Collection<?>) value;
            if (CollectionUtils.isEmpty(collection)) {
                return;
            }
            params.notIn(column, collection);
        }
    };

    public abstract void parse(QueryParams<?> params, String column, Object value);
}
