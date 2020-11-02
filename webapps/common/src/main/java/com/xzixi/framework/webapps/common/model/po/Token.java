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

package com.xzixi.framework.webapps.common.model.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "令牌")
public class Token implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标识")
    private String signature;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "登陆时间")
    private Long loginTime;

    @ApiModelProperty(value = "过期时间")
    private Long expireTime;
}
