package com.xzixi.self.portal.framework.webmvc.model.search.annotation;

import com.xzixi.self.portal.framework.webmvc.model.search.ConditionType;

import java.lang.annotation.*;

/**
 * 等于
 *
 * @author 薛凌康
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Condition(ConditionType.EQ)
public @interface Eq {

    /** 列名 */
    String value() default "";
}
