package com.xzixi.framework.boot.webmvc.model.search.annotation;

import com.xzixi.framework.boot.webmvc.model.search.ConditionType;

import java.lang.annotation.*;

/**
 * @author 薛凌康
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Condition(ConditionType.IN)
public @interface In {

    /** 列名 */
    String value() default "";
}