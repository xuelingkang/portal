package com.xzixi.self.portal.framework.config.cache.annotation;

import com.xzixi.self.portal.framework.config.cache.RedisCacheConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RedisCacheConfig.class)
public @interface EnableFrameworkCache {
}
