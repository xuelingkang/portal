package com.xzixi.framework.boot.webmvc.config.validation.annotation;

import com.xzixi.framework.boot.webmvc.config.validation.ValidationConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ValidationConfig.class)
public @interface EnableFrameworkValidation {
}
