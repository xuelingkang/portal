package com.xzixi.self.portal.webapp.framework.model.annotation;

import java.lang.annotation.*;

/**
 * 大于
 *
 * @author 薛凌康
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Gt {

    /** 列名 */
    String value() default "";
}
