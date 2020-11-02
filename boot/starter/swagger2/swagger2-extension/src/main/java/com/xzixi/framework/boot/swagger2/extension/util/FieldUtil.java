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

package com.xzixi.framework.boot.swagger2.extension.util;

import java.lang.reflect.Field;

/**
 * @author 薛凌康
 */
public class FieldUtil {

    /**
     * 按照名称获取字段
     *
     * @param cls 类对象
     * @param name 字段名
     * @return 目标字段
     */
    public static Field getDeclaredField(Class<?> cls, String name) {
        Field field = null;
        try {
            if (cls==null) {
                return null;
            }
            field = cls.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            field = getDeclaredField(cls.getSuperclass(), name);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return field;
    }
}
