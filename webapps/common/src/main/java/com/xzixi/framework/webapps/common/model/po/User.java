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

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.xzixi.framework.webapps.common.model.enums.Sex;
import com.xzixi.framework.webapps.common.model.enums.UserType;
import com.xzixi.framework.webapps.common.model.valid.UserSave;
import com.xzixi.framework.webapps.common.model.valid.UserUpdate;
import com.xzixi.framework.webapps.common.model.valid.WebsiteUserSave;
import com.xzixi.framework.boot.swagger2.annotations.IgnoreSwagger2Parameter;
import com.xzixi.framework.boot.webmvc.model.BaseModel;
import com.xzixi.framework.boot.webmvc.model.IBelonging;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

/**
 * 用户
 *
 * @author 薛凌康
 */
@Data
@ApiModel(description = "用户")
public class User extends BaseModel implements IBelonging {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    @Null(groups = {WebsiteUserSave.class, UserSave.class}, message = "用户id必须为空！")
    @NotNull(groups = {UserUpdate.class}, message = "用户id不能为空！")
    private Integer id;

    @ApiModelProperty(value = "用户名")
    @NotBlank(groups = {WebsiteUserSave.class, UserSave.class}, message = "用户名不能为空！")
    @Length(groups = {WebsiteUserSave.class, UserSave.class}, min = 6, max = 20, message = "用户名不能小于6位且不能大于20位！")
    @Null(groups = {UserUpdate.class}, message = "用户名必须为空！")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotEmpty(groups = {WebsiteUserSave.class, UserSave.class}, message = "密码不能为空！")
    @Length(groups = {WebsiteUserSave.class, UserSave.class}, min = 6, max = 16, message = "密码不能小于6位且不能大于16位！")
    private String password;

    @ApiModelProperty(value = "邮箱")
    @NotBlank(groups = {WebsiteUserSave.class, UserSave.class}, message = "邮箱不能为空！")
    @Email(groups = {WebsiteUserSave.class, UserSave.class}, message = "邮箱格式不正确！")
    @Null(groups = {UserUpdate.class}, message = "邮箱必须为空！")
    @Length(groups = {WebsiteUserSave.class, UserSave.class}, max = 50, message = "邮箱不能大于50字！")
    private String email;

    @ApiModelProperty(value = "昵称")
    @NotBlank(groups = {WebsiteUserSave.class, UserSave.class}, message = "昵称不能为空！")
    @Length(groups = {WebsiteUserSave.class, UserSave.class, UserUpdate.class}, max = 50, message = "昵称不能大于50字！")
    private String nickname;

    @ApiModelProperty(value = "性别")
    @NotNull(groups = {WebsiteUserSave.class, UserSave.class}, message = "性别不能为空！")
    private Sex sex;

    @ApiModelProperty(value = "生日")
    @NotNull(groups = {WebsiteUserSave.class, UserSave.class}, message = "生日不能为空！")
    private Long birth;

    @ApiModelProperty(value = "用户类型")
    @NotNull(groups = {UserSave.class}, message = "用户类型不能为空！")
    private UserType type;

    @ApiModelProperty(value = "创建时间")
    private Long createTime;

    @ApiModelProperty(value = "登录时间")
    private Long loginTime;

    @ApiModelProperty(value = "是否激活")
    private Boolean activated;

    @ApiModelProperty(value = "是否锁定")
    private Boolean locked;

    @ApiModelProperty(value = "是否删除")
    @TableLogic
    @IgnoreSwagger2Parameter
    private Boolean deleted;

    @Override
    public Integer ownerId() {
        return id;
    }
}
