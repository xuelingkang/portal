package com.xzixi.self.portal.framework.config.async.annotation;

import com.xzixi.self.portal.framework.config.async.AsyncTaskExecutorConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AsyncTaskExecutorConfig.class)
public @interface EnableFrameworkAsync {
}
