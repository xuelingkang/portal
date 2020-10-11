package com.xzixi.framework.boot.webmvc.model.search.annotation;

import com.xzixi.framework.boot.webmvc.model.search.ConditionType;

import java.lang.annotation.*;

/**
 * 小于等于
 *
 * @author 薛凌康
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Condition(ConditionType.LE)
public @interface Le {

    /** 列名 */
    String value() default "";
}
