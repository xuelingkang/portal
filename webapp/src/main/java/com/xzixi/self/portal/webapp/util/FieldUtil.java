package com.xzixi.self.portal.webapp.util;

import com.xzixi.self.portal.webapp.exception.LogicException;
import com.xzixi.self.portal.webapp.exception.ProjectException;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

/**
 * @author 薛凌康
 */
public class FieldUtil {

    @SuppressWarnings("unchecked")
    public static <T> T get(Object object, String fieldName) {
        check(object, fieldName);
        Class<?> cls = object.getClass();
        Field field = getDeclaredField(cls, fieldName);
        if (field == null) {
            throw new ProjectException(String.format("类%s没有字段%s！", cls.getName(), fieldName));
        }
        field.setAccessible(true);
        T value;
        try {
            value = (T) field.get(object);
        } catch (IllegalAccessException e) {
            throw new ProjectException(String.format("类%s的字段%s没有访问权限！", cls.getName(), fieldName), e);
        }
        return value;
    }

    public static void set(Object object, String fieldName, Object value) {
        check(object, fieldName);
        Class<?> cls = object.getClass();
        Field field = getDeclaredField(cls, fieldName);
        if (field == null) {
            throw new ProjectException(String.format("类%s没有字段%s！", cls.getName(), fieldName));
        }
        field.setAccessible(true);
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            throw new ProjectException(String.format("类%s的字段%s没有访问权限！", cls.getName(), fieldName), e);
        }
    }

    private static void check(Object object, String fieldName) {
        if (object == null) {
            throw new LogicException(400, "object不能为null！");
        }
        if (StringUtils.isBlank(fieldName)) {
            throw new LogicException(400, "fieldName不能为空！");
        }
    }

    /**
     * 按照名称获取字段
     *
     * @param cls 类对象
     * @param fieldName 字段名
     * @return 目标字段
     */
    private static Field getDeclaredField(Class<?> cls, String fieldName) {
        Field field;
        try {
            if (cls == null) {
                return null;
            }
            field = cls.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            field = getDeclaredField(cls.getSuperclass(), fieldName);
        }
        return field;
    }
}
