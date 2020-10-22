package com.xzixi.framework.webapps.task.aspect;

import com.xzixi.framework.boot.webmvc.aspect.BaseControllerExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常拦截
 *
 * @author 薛凌康
 */
@RestControllerAdvice
public class ControllerExceptionHandler extends BaseControllerExceptionHandler {
}
