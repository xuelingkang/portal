package com.xzixi.self.portal.webapp.model.po;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.xzixi.self.portal.webapp.model.valid.SystemUserInsert;
import com.xzixi.self.portal.webapp.model.valid.WebsiteUserInsert;
import com.xzixi.self.portal.webapp.framework.model.BaseModel;
import com.xzixi.self.portal.webapp.model.enums.Sex;
import com.xzixi.self.portal.webapp.model.enums.UserType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 用户
 *
 * @author 薛凌康
 */
@Data
@ApiModel(value = "用户")
public class User extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private Integer id;
    @ApiModelProperty(value = "用户名")
    @NotBlank(groups = {WebsiteUserInsert.class, SystemUserInsert.class}, message = "用户名不能为空！")
    private String username;
    @ApiModelProperty(value = "密码")
    @NotEmpty(groups = {WebsiteUserInsert.class, SystemUserInsert.class}, message = "密码不能为空！")
    private String password;
    @ApiModelProperty(value = "邮箱")
    @NotBlank(groups = {WebsiteUserInsert.class, SystemUserInsert.class}, message = "邮箱不能为空！")
    private String email;
    @ApiModelProperty(value = "昵称")
    @NotBlank(groups = {WebsiteUserInsert.class, SystemUserInsert.class}, message = "昵称不能为空！")
    private String nickname;
    @ApiModelProperty(value = "性别", allowableValues = "MALE,FEMALE")
    @NotNull(groups = {WebsiteUserInsert.class, SystemUserInsert.class}, message = "性别不能为空！")
    private Sex sex;
    @ApiModelProperty(value = "生日")
    @NotNull(groups = {WebsiteUserInsert.class, SystemUserInsert.class}, message = "生日不能为空！")
    private Long birth;
    @ApiModelProperty(value = "用户类型", allowableValues = "SYSTEM,WEBSITE")
    @NotNull(groups = {SystemUserInsert.class}, message = "用户类型不能为空！")
    private UserType type;
    @ApiModelProperty(value = "创建时间")
    private Long createTime;
    @ApiModelProperty(value = "登录时间")
    private Long loginTime;
    @ApiModelProperty(value = "是否锁定", allowableValues = "true,false")
    private Boolean locked;
    @ApiModelProperty(value = "是否删除", allowableValues = "true,false")
    @TableLogic
    private Boolean deleted;
}
