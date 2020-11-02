/*
 * The xzixi framework is based on spring framework, which simplifies development.
 * Copyright (C) 2020  xuelingkang@163.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.xzixi.framework.webapps.sso.server.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * sso-client登录成功响应
 *
 * @author xuelingkang
 * @date 2020-10-27
 */
@Data
@ApiModel(description = "sso-client登录成功响应")
public class SsoClientLoginResponse {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "x-access-token")
    private String accessToken;

    @ApiModelProperty(value = "x-refresh-token")
    private String refreshToken;

    @ApiModelProperty(value = "登录时间")
    private Long loginTime;

    @ApiModelProperty(value = "x-access-token失效时间")
    private Long accessExpireTime;

    @ApiModelProperty(value = "x-refresh-token失效时间")
    private Long refreshExpireTime;
}