package com.xzixi.self.portal.webapp.framework.model.annotation;

import java.lang.annotation.*;

/**
 * 小于等于
 *
 * @author 薛凌康
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Parser("le")
public @interface Le {

    /** 列名 */
    String value() default "";
}
