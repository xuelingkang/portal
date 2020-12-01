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

package com.xzixi.framework.webapps.sso.server.controller;

import com.xzixi.framework.boot.core.model.Result;
import com.xzixi.framework.webapps.sso.server.exception.AccessTokenExpireException;
import com.xzixi.framework.webapps.sso.server.exception.AuthException;
import com.xzixi.framework.webapps.sso.server.exception.RefreshTokenExpireException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author xuelingkang
 * @date 2020-11-06
 */
@RestControllerAdvice
public class ControllerExceptionHandler {

    /**
     * accessToken过期
     *
     * @return Result
     */
    @ExceptionHandler(AccessTokenExpireException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result<?> handleAccessTokenExpireException() {
        return new Result<>(HttpStatus.UNAUTHORIZED.value(), 1);
    }

    /**
     * refreshToken过期或认证异常
     *
     * @return Result
     */
    @ExceptionHandler({RefreshTokenExpireException.class, AuthException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result<?> handleRefreshTokenExpireException() {
        return new Result<>(HttpStatus.UNAUTHORIZED.value(), 0);
    }
}
