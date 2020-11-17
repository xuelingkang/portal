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

package com.xzixi.framework.webapps.common.model.vo;

import com.xzixi.framework.boot.core.util.BeanUtils;
import com.xzixi.framework.webapps.common.model.po.Authority;
import com.xzixi.framework.webapps.common.model.po.Role;
import com.xzixi.framework.webapps.common.model.po.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * @author 薛凌康
 */
@Data
@NoArgsConstructor
@ApiModel(description = "用户")
public class UserVO extends User {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色集合")
    private Collection<Role> roles;

    @ApiModelProperty(value = "权限集合")
    private Collection<Authority> authorities;

    @ApiModelProperty(value = "权限标识")
    private Collection<String> authoritySignals;

    public UserVO(User user, String... ignoreProperties) {
        BeanUtils.copyProperties(user, this, ignoreProperties);
    }

    @Getter
    @AllArgsConstructor
    public static class BuildOption {
        private boolean roles;
        private boolean authorities;
    }
}
