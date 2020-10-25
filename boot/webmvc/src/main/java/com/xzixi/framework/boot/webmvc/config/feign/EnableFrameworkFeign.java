package com.xzixi.framework.boot.webmvc.config.feign;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author xuelingkang
 * @date 2020-10-25
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(FeignConfig.class)
public @interface EnableFrameworkFeign {
}
