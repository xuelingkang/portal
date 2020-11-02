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

import com.xzixi.framework.webapps.common.model.enums.AuthorityCategory;
import com.xzixi.framework.webapps.common.model.enums.AuthorityMethod;
import com.xzixi.framework.webapps.common.model.enums.AuthorityProtocol;
import com.xzixi.framework.webapps.common.model.valid.AuthoritySave;
import com.xzixi.framework.webapps.common.model.valid.AuthorityUpdate;
import com.xzixi.framework.boot.webmvc.model.BaseModel;
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
@ApiModel(description = "权限")
public class Authority extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限id")
    @Null(groups = {AuthoritySave.class}, message = "权限id必须为空！")
    @NotNull(groups = {AuthorityUpdate.class}, message = "权限id不能为空！")
    private Integer id;

    @ApiModelProperty(value = "协议类型")
    @NotNull(groups = {AuthoritySave.class}, message = "协议类型不能为空！")
    private AuthorityProtocol protocol;

    @ApiModelProperty(value = "权限类别")
    @NotNull(groups = {AuthoritySave.class}, message = "权限类别不能为空！")
    private AuthorityCategory category;

    @ApiModelProperty(value = "权限顺序")
    @NotNull(groups = {AuthoritySave.class}, message = "权限顺序不能为空！")
    private Integer seq;

    @ApiModelProperty(value = "权限路径")
    @NotNull(groups = {AuthoritySave.class}, message = "权限路径不能为空！")
    @Length(groups = {AuthoritySave.class, AuthorityUpdate.class}, max = 50, message = "权限路径不能超过50字！")
    private String pattern;

    @ApiModelProperty(value = "请求方法")
    private AuthorityMethod method;

    @ApiModelProperty(value = "权限描述")
    @NotNull(groups = {AuthoritySave.class}, message = "权限描述不能为空！")
    @Length(groups = {AuthoritySave.class, AuthorityUpdate.class}, max = 200, message = "权限描述不能超过200字！")
    private String description;

    @ApiModelProperty(value = "应用id")
    private AuthorityMethod appId;
}
