package com.xzixi.self.portal.webapp.framework.model.annotation;

import java.lang.annotation.*;

/**
 * @author 薛凌康
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Parser {

    /** 转换方法的名称 */
    String value();

    /** 转换方法的参数列表 */
    Class<?>[] parameterTypes() default {String.class, Object.class};
}
