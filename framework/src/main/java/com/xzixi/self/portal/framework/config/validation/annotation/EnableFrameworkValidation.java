package com.xzixi.self.portal.framework.config.validation.annotation;

import com.xzixi.self.portal.framework.config.validation.ValidationConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ValidationConfig.class)
public @interface EnableFrameworkValidation {
}
