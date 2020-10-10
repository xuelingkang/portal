package com.xzixi.self.portal.framework.webmvc.model.search.annotation;

import com.xzixi.self.portal.framework.webmvc.model.search.ConditionType;

import java.lang.annotation.*;

/**
 * @author 薛凌康
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Condition {

    ConditionType value();

    /** 忽略null值 */
    boolean ignoreNull() default true;
}
