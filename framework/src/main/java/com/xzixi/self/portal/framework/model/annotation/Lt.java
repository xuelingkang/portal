package com.xzixi.self.portal.framework.model.annotation;

import java.lang.annotation.*;

/**
 * 小于
 *
 * @author 薛凌康
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Lt {

    /** 列名 */
    String value() default "";
}
