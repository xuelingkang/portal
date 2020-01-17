package com.xzixi.self.portal.framework.util;

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
                || URL.class==clazz || Locale.class==clazz || Class.class==clazz);
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
