package com.xzixi.self.portal.framework.model.annotation;

import java.lang.annotation.*;

/**
 * 等于
 *
 * @author 薛凌康
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Eq {

    /** 列名 */
    String value() default "";
}
