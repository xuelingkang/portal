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

package com.xzixi.framework.webapps.common.model.enums;

import com.xzixi.framework.boot.core.model.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 请求方法
 *
 * @author 薛凌康
 */
@Getter
@AllArgsConstructor
public enum AuthorityMethod implements IBaseEnum {

    /** 所有请求 */
    ALL("所有请求"),
    /** http get请求 */
    GET("GET请求"),
    /** http head请求 */
    HEAD("HEAD请求"),
    /** http delete请求 */
    DELETE("DELETE请求"),
    /** http post请求 */
    POST("POST请求"),
    /** http put请求 */
    PUT("PUT请求"),
    /** http patch请求 */
    PATCH("PATCH请求"),
    /** stomp subscribe请求 */
    SUBSCRIBE("SUBSCRIBE请求");

    private final String value;
}
