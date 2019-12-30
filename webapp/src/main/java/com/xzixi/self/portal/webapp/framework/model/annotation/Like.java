package com.xzixi.self.portal.webapp.framework.model.annotation;

import java.lang.annotation.*;

/**
 * 模糊查询
 *
 * @author 薛凌康
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Parser("like")
public @interface Like {

    /** 列名 */
    String value() default "";
}
