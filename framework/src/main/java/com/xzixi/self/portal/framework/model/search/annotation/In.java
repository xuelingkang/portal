package com.xzixi.self.portal.framework.model.search.annotation;

import com.xzixi.self.portal.framework.model.search.ConditionType;

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