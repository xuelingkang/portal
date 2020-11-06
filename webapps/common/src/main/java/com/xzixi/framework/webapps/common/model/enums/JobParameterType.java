/*
 * The xzixi framework is based on spring framework, which simplifies development.
 * Copyright (C) 2020  xuelingkang@163.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.xzixi.framework.webapps.common.model.enums;

import com.xzixi.framework.boot.core.model.IBaseEnum;
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

    private final String value;

    /**
     * 检查传入的值是否与当前类型匹配
     *
     * @param value 要检查的值
     * @return {@code true} 匹配 {@code false} 不匹配
     */
    public abstract boolean match(String value);
}
