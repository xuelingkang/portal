package com.xzixi.self.portal.framework.model.search.annotation;

import com.xzixi.self.portal.framework.model.search.ConditionType;

import java.lang.annotation.*;

/**
 * 模糊查询
 *
 * @author 薛凌康
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Condition(ConditionType.LIKE)
public @interface Like {

    /** 列名 */
    String value() default "";
}
