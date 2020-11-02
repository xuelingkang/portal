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

package com.xzixi.framework.webapps.common.model.vo;

import com.xzixi.framework.boot.webmvc.util.BeanUtils;
import com.xzixi.framework.webapps.common.model.po.Token;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 薛凌康
 */
@Data
@NoArgsConstructor
@ApiModel(description = "令牌")
public class TokenVO extends Token {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户")
    private UserVO user;

    public TokenVO(Token token, String... ignoreProperties) {
        BeanUtils.copyProperties(token, this, ignoreProperties);
    }
}
