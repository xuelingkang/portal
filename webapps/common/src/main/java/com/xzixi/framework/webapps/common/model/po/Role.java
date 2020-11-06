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

import com.xzixi.framework.boot.core.model.BaseModel;
import com.xzixi.framework.webapps.common.model.enums.AuthorityMethod;
import com.xzixi.framework.webapps.common.model.valid.RoleSave;
import com.xzixi.framework.webapps.common.model.valid.RoleUpdate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "角色")
public class Role extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色id")
    @Null(groups = {RoleSave.class}, message = "角色id必须为空！")
    @NotNull(groups = {RoleUpdate.class}, message = "角色id不能为空！")
    private Integer id;

    @ApiModelProperty(value = "角色名称")
    @NotNull(groups = {RoleSave.class}, message = "角色名称不能为空！")
    @Length(groups = {RoleSave.class, RoleUpdate.class}, max = 20, message = "角色名称不能超过20字！")
    private String name;

    @ApiModelProperty(value = "角色顺序")
    @NotNull(groups = {RoleSave.class}, message = "角色顺序不能为空！")
    private Integer seq;

    @ApiModelProperty(value = "是否游客用户的默认角色")
    @NotNull(groups = {RoleSave.class}, message = "是否游客用户的默认角色不能为空！")
    private Boolean guest;

    @ApiModelProperty(value = "是否网站用户的默认角色")
    @NotNull(groups = {RoleSave.class}, message = "是否网站用户的默认角色不能为空！")
    private Boolean website;

    @ApiModelProperty(value = "角色描述")
    @NotNull(groups = {RoleSave.class}, message = "角色描述不能为空！")
    @Length(groups = {RoleSave.class, RoleUpdate.class}, max = 20, message = "角色描述不能超过20字！")
    private String description;

    @ApiModelProperty(value = "应用id")
    private AuthorityMethod appId;
}
