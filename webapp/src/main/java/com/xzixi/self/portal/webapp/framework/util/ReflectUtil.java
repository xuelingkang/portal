package com.xzixi.self.portal.webapp.framework.util;

import com.xzixi.self.portal.webapp.framework.exception.ProjectException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author 薛凌康
 */
public class ReflectUtil {

    public static <T> T newInstance(Class<T> cls) {
        try {
            return cls.newInstance();
        } catch (InstantiationException e) {
            throw new ProjectException(String.format("类(%s)不能被初始化！", cls.getName()), e);
        } catch (IllegalAccessException e) {
            throw new ProjectException(String.format("无法访问类(%s)的无参构造器！", cls.getName()), e);
        }
    }

    /**
     * 获取所有属性
     *
     * @param cls 类
     * @return Field[]
     */
    public static Field[] getDeclaredFields(Class<?> cls) {
        Field[] fields = cls.getDeclaredFields();
        while (cls.getSuperclass() != Object.class) {
            ArrayUtils.add(fields, getDeclaredFields(cls.getSuperclass()));
        }
        return fields;
    }

    private static final Pattern NAME_PATTERN = Pattern.compile("^\\w+[\\w\\d_$]*$");

    /**
     * 反射调用方法
     *
     * @param instance 调用实例
     * @param methodName 方法名称
     * @param parameterTypes 参数类型列表
     * @param parameters 参数列表
     * @param <T> 返回值类型
     * @return 方法返回值
     */
    @SuppressWarnings("unchecked")
    public static <T> T invokeMethod(Object instance, String methodName, Class<?>[] parameterTypes, Object... parameters) {
        if (instance == null) {
            throw new ProjectException("instance不能为null！");
        }
        if (!NAME_PATTERN.matcher(methodName).matches()) {
            throw new ProjectException(String.format("方法名称(%s)不合法！", methodName));
        }

        Method method;
        try {
            method = instance.getClass().getDeclaredMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            String errMsg = String.format(
                    "类(%s)没有方法(%s(%s))！",
                    instance.getClass().getName(),
                    methodName,
                    StringUtils.join(Arrays.stream(parameterTypes).map(Class::getName).collect(Collectors.toList()), ","));
            throw new ProjectException(errMsg, e);
        }

        method.setAccessible(true);
        Object result;
        try {
            result = method.invoke(instance, parameters);
        } catch (IllegalAccessException e) {
            String errMsg = String.format(
                    "无法访问类(%s)的方法(%s(%s))！",
                    instance.getClass().getName(),
                    methodName,
                    StringUtils.join(Arrays.stream(parameterTypes).map(Class::getName).collect(Collectors.toList()), ","));
            throw new ProjectException(errMsg, e);
        } catch (InvocationTargetException e) {
            String errMsg = String.format(
                    "调用类(%s)的方法(%s(%s))出错！",
                    instance.getClass().getName(),
                    methodName,
                    StringUtils.join(Arrays.stream(parameterTypes).map(Class::getName).collect(Collectors.toList()), ","));
            throw new ProjectException(errMsg, e);
        }

        return (T) result;
    }

    /**
     * 反射获取对象属性值
     *
     * @param instance 对象
     * @param propName 属性名称
     * @param <T> 属性值类型
     * @return 属性值
     */
    public static <T> T getProp(Object instance, String propName) {
        if (instance == null) {
            throw new ProjectException("instance不能为null！");
        }
        if (!NAME_PATTERN.matcher(propName).matches()) {
            throw new ProjectException(String.format("属性名称(%s)不合法！", propName));
        }
        Class<?> cls = instance.getClass();
        Field field = getDeclaredField(cls, propName);
        if (field == null) {
            throw new ProjectException(String.format("类(%s)没有属性(%s)！", cls.getName(), propName));
        }

        return getProp(instance, field);
    }

    /**
     * 反射获取对象属性值
     *
     * @param instance 对象
     * @param field 属性
     * @param <T> 属性值类型
     * @return 属性值
     */
    @SuppressWarnings("unchecked")
    public static <T> T getProp(Object instance, Field field) {
        if (instance == null) {
            throw new ProjectException("instance不能为null！");
        }
        field.setAccessible(true);
        try {
            return (T) field.get(instance);
        } catch (IllegalAccessException e) {
            String errMsg = String.format("无法访问类(%s)的属性(%s)！", instance.getClass().getName(), field.getName());
            throw new ProjectException(errMsg, e);
        }
    }

    /**
     * 反射设置对象属性的值
     *
     * @param instance 对象
     * @param propName 属性名称
     * @param value 属性值
     */
    public static void setProp(Object instance, String propName, Object value) {
        if (instance == null) {
            throw new ProjectException("instance不能为null！");
        }
        if (!NAME_PATTERN.matcher(propName).matches()) {
            throw new ProjectException(String.format("属性名称(%s)不合法！", propName));
        }
        Class<?> cls = instance.getClass();
        Field field = getDeclaredField(cls, propName);
        if (field == null) {
            throw new ProjectException(String.format("类(%s)没有属性(%s)！", cls.getName(), propName));
        }

        setProp(instance, field, value);
    }

    /**
     * 反射设置对象属性的值
     *
     * @param instance 对象
     * @param field 属性
     * @param value 属性值
     */
    public static void setProp(Object instance, Field field, Object value) {
        if (instance == null) {
            throw new ProjectException("instance不能为null！");
        }
        field.setAccessible(true);
        try {
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            String errMsg = String.format("无法访问类(%s)的属性(%s)！", instance.getClass().getName(), field.getName());
            throw new ProjectException(errMsg, e);
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
            if (cls == Object.class) {
                return null;
            }
            field = cls.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            field = getDeclaredField(cls.getSuperclass(), fieldName);
        }
        return field;
    }
}
