package com.xzixi.self.portal.swagger2.extension.util;

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
