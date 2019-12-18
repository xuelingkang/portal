package com.xzixi.self.portal.webapp.framework.util;

import org.springframework.util.ClassUtils;

import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

public class TypeUtil {

    /**
     * 判断是否基本类型或字面量类型
     *
     * @param clazz
     * @return
     */
    public static boolean isSimpleValueType(Class<?> clazz) {
        return (ClassUtils.isPrimitiveOrWrapper(clazz) || clazz.isEnum() || CharSequence.class.isAssignableFrom(clazz)
                || Number.class.isAssignableFrom(clazz) || Date.class.isAssignableFrom(clazz) || URI.class==clazz
                || URL.class==clazz || Locale.class==clazz || Class.class==clazz);
    }

    /**
     * 判断是否数组类型
     *
     * @param clazz
     * @return
     */
    public static boolean isArrayType(Class<?> clazz) {
        return clazz.isArray();
    }

    /**
     * 判断是否集合类型
     *
     * @param clazz
     * @return
     */
    public static boolean isCollectionType(Class<?> clazz) {
        return Collection.class.isAssignableFrom(clazz);
    }
}
