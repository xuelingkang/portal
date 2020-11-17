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

package com.xzixi.framework.webapps.common.model.po;

import com.xzixi.framework.boot.core.model.BaseModel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 薛凌康
 */
@Data
@NoArgsConstructor
public class UserRoleLink extends BaseModel {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer userId;

    private Integer roleId;

    public UserRoleLink(Integer userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}
