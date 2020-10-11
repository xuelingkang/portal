package com.xzixi.self.portal.framework.webmvc.model.search.annotation;

import com.xzixi.self.portal.framework.webmvc.model.search.ConditionType;

import java.lang.annotation.*;

/**
 * @author 薛凌康
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Condition(ConditionType.NOT_IN)
public @interface NotIn {

    /** 列名 */
    String value() default "";
}
