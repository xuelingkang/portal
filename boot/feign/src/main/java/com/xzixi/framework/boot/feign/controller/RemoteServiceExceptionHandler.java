/*
 * The spring-based xzixi framework simplifies development.
 *
 * Copyright (C) 2020  xuelingkang@163.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.xzixi.framework.boot.feign.controller;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.xzixi.framework.boot.core.model.Result;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

/**
 * @author xuelingkang
 * @date 2020-11-21
 */
@Slf4j
@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE - 1)
public class RemoteServiceExceptionHandler {

    /**
     * 调用远程接口异常，且没有fallback时，返回远程接口的异常
     *
     * @param e HystrixRuntimeException
     * @param response HttpServletResponse
     * @return Result
     */
    @ExceptionHandler({HystrixRuntimeException.class})
    public Result<?> handleFeignException(HystrixRuntimeException e, HttpServletResponse response) {
        Throwable cause = e.getCause();
        if (cause instanceof FeignException) {
            FeignException feignException = (FeignException) cause;
            String body = feignException.contentUTF8();
            Result<?> result = JSON.parseObject(body, Result.class);
            response.setStatus(result.getCode());
            return result;
        }
        log.error(e.getMessage(), e);
        return new Result<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务端异常！", null);
    }
}
