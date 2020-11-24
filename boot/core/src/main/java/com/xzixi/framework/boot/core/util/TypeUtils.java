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

package com.xzixi.framework.boot.core.util;

import org.springframework.util.ClassUtils;

import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

/**
 * @author 薛凌康
 */
public class TypeUtils {

    /**
     * 判断是否基本类型或字面量类型
     *
     * @param clazz Class
     * @return boolean
     */
    public static boolean isSimpleValueType(Class<?> clazz) {
        return (ClassUtils.isPrimitiveOrWrapper(clazz) || clazz.isEnum() || CharSequence.class.isAssignableFrom(clazz)
                || Number.class.isAssignableFrom(clazz) || Date.class.isAssignableFrom(clazz) || URI.class==clazz
                || URL.class==clazz || Locale.class==clazz || Class.class==clazz) || clazz.isEnum();
    }

    /**
     * 判断是否数组类型
     *
     * @param clazz Class
     * @return boolean
     */
    public static boolean isArrayType(Class<?> clazz) {
        return clazz.isArray();
    }

    /**
     * 判断是否集合类型
     *
     * @param clazz Class
     * @return boolean
     */
    public static boolean isCollectionType(Class<?> clazz) {
        return Collection.class.isAssignableFrom(clazz);
    }

    /**
     * 有些类型不能直接用来当做查询参数，如enum
     *
     * @param value 原始值
     * @return 转换后的值
     */
    public static Object parseObject(Object value) {
        if (value == null) {
            return null;
        }
        if (value.getClass().isEnum()) {
            Enum<?> e = (Enum<?>) value;
            return e.name();
        }
        return value;
    }
}
