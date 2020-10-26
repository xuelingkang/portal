package com.xzixi.framework.webapps.common.component.annotation;

import com.xzixi.framework.webapps.common.component.Components;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author xuelingkang
 * @date 2020-10-26
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({Components.class})
public @interface EnableCommonComponents {
}
