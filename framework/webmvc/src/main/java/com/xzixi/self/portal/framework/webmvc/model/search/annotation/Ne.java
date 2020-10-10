package com.xzixi.self.portal.framework.webmvc.model.search.annotation;

import com.xzixi.self.portal.framework.webmvc.model.search.ConditionType;

import java.lang.annotation.*;

/**
 * 不等于
 *
 * @author 薛凌康
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Condition(ConditionType.NE)
public @interface Ne {

    /** 列名 */
    String value() default "";
}
