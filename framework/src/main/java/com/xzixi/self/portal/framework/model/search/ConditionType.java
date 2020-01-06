package com.xzixi.self.portal.framework.model.search;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @author 薛凌康
 */
public enum ConditionType {

    /** 等于 */
    EQ() {
        @Override
        public void parse(QueryWrapper<?> wrapper, String column, Object value) {
            if (value == null) {
                wrapper.isNotNull(column);
                return;
            }
            wrapper.eq(column, value);
        }
    },
    /** 不等于 */
    NE() {
        @Override
        public void parse(QueryWrapper<?> wrapper, String column, Object value) {
            if (value == null) {
                wrapper.isNotNull(column);
                return;
            }
            wrapper.ne(column, value);
        }
    },
    /** 大于 */
    GT() {
        @Override
        public void parse(QueryWrapper<?> wrapper, String column, Object value) {
            if (value == null) {
                return;
            }
            wrapper.gt(column, value);
        }
    },
    /** 大于等于 */
    GE() {
        @Override
        public void parse(QueryWrapper<?> wrapper, String column, Object value) {
            if (value == null) {
                return;
            }
            wrapper.ge(column, value);
        }
    },
    /** 小于 */
    LT() {
        @Override
        public void parse(QueryWrapper<?> wrapper, String column, Object value) {
            if (value == null) {
                return;
            }
            wrapper.lt(column, value);
        }
    },
    /** 小于等于 */
    LE() {
        @Override
        public void parse(QueryWrapper<?> wrapper, String column, Object value) {
            if (value == null) {
                return;
            }
            wrapper.le(column, value);
        }
    },
    /** 模糊搜索 */
    LIKE() {
        @Override
        public void parse(QueryWrapper<?> wrapper, String column, Object value) {
            if (value == null) {
                return;
            }
            wrapper.like(column, value);
        }
    };

    public abstract void parse(QueryWrapper<?> wrapper, String column, Object value);
}
