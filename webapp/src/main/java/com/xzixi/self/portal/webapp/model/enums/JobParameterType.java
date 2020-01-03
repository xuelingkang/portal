package com.xzixi.self.portal.webapp.model.enums;

import com.xzixi.self.portal.framework.model.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * @author 薛凌康
 */
@Getter
@AllArgsConstructor
public enum JobParameterType implements IBaseEnum {

    /** int型整数 */
    INTEGER("int型整数") {
        @Override
        public boolean match(String value) {
            try {
                Integer i = NumberUtils.createInteger(value);
                if (i == null) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
            return true;
        }
    },
    /** long型整数 */
    LONG("long型整数") {
        @Override
        public boolean match(String value) {
            try {
                Long l = NumberUtils.createLong(value);
                if (l == null) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
            return true;
        }
    },
    /** 浮点型 */
    DOUBLE("浮点型") {
        @Override
        public boolean match(String value) {
            try {
                Double d = NumberUtils.createDouble(value);
                if (d == null) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
            return true;
        }
    },
    /** 字符串 */
    STRING("字符串") {
        @Override
        public boolean match(String value) {
            return true;
        }
    };

    private String value;

    /**
     * 检查传入的值是否与当前类型匹配
     *
     * @param value 要检查的值
     * @return {@code true} 匹配 {@code false} 不匹配
     */
    public abstract boolean match(String value);
}
