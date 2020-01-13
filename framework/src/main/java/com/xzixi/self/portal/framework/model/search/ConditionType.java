package com.xzixi.self.portal.framework.model.search;

/**
 * @author 薛凌康
 */
public enum ConditionType {

    /** 等于 */
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
    /** 不等于 */
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
    /** 大于 */
    GT() {
        @Override
        public void parse(QueryParams<?> params, String column, Object value) {
            if (value == null) {
                return;
            }
            params.gt(column, value);
        }
    },
    /** 大于等于 */
    GE() {
        @Override
        public void parse(QueryParams<?> params, String column, Object value) {
            if (value == null) {
                return;
            }
            params.ge(column, value);
        }
    },
    /** 小于 */
    LT() {
        @Override
        public void parse(QueryParams<?> params, String column, Object value) {
            if (value == null) {
                return;
            }
            params.lt(column, value);
        }
    },
    /** 小于等于 */
    LE() {
        @Override
        public void parse(QueryParams<?> params, String column, Object value) {
            if (value == null) {
                return;
            }
            params.le(column, value);
        }
    },
    /** 模糊搜索 */
    LIKE() {
        @Override
        public void parse(QueryParams<?> params, String column, Object value) {
            if (value == null) {
                return;
            }
            params.like(column, value);
        }
    };

    public abstract void parse(QueryParams<?> params, String column, Object value);
}
