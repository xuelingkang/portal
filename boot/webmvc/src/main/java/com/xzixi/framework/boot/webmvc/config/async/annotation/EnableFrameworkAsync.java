package com.xzixi.framework.boot.webmvc.config.async.annotation;

import com.xzixi.framework.boot.webmvc.config.async.AsyncTaskExecutorConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AsyncTaskExecutorConfig.class)
public @interface EnableFrameworkAsync {
}
