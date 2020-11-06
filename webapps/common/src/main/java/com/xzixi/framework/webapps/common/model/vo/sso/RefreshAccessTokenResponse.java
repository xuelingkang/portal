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

package com.xzixi.framework.webapps.common.model.vo.sso;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xuelingkang
 * @date 2020-11-05
 */
@Data
@ApiModel(description = "刷新accessToken响应")
public class RefreshAccessTokenResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "新的accessToken")
    private String accessToken;

    @ApiModelProperty(value = "登录用户id")
    private Integer userId;

    @ApiModelProperty(value = "新的accessToken的过期时间")
    private Long expireTime;
}
