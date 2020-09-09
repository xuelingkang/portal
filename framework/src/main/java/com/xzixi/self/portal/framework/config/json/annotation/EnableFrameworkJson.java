package com.xzixi.self.portal.framework.config.json.annotation;

import com.xzixi.self.portal.framework.config.json.FastJsonConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(FastJsonConfig.class)
public @interface EnableFrameworkJson {
}
