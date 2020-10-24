package com.xzixi.framework.boot.webmvc.config.exception.annotation;

import com.xzixi.framework.boot.webmvc.config.exception.handler.ControllerExceptionHandler;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author xuelingkang
 * @date 2020-10-24
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ControllerExceptionHandler.class)
public @interface EnableFrameworkExceptionHandler {
}
