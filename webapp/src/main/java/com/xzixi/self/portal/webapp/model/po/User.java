package com.xzixi.self.portal.webapp.model.po;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.xzixi.self.portal.extension.swagger2.annotation.IgnoreSwagger2Parameter;
import com.xzixi.self.portal.webapp.framework.model.BaseModel;
import com.xzixi.self.portal.webapp.framework.model.IBelonging;
import com.xzixi.self.portal.webapp.model.enums.Sex;
import com.xzixi.self.portal.webapp.model.enums.UserType;
import com.xzixi.self.portal.webapp.model.valid.UserSave;
import com.xzixi.self.portal.webapp.model.valid.UserUpdate;
import com.xzixi.self.portal.webapp.model.valid.WebsiteUserSave;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
    private String username;

    @ApiModelProperty(value = "密码")
    @NotEmpty(groups = {WebsiteUserSave.class, UserSave.class}, message = "密码不能为空！")
    private String password;

    @ApiModelProperty(value = "邮箱")
    @NotBlank(groups = {WebsiteUserSave.class, UserSave.class}, message = "邮箱不能为空！")
    @Email(groups = {WebsiteUserSave.class, UserSave.class}, message = "邮箱格式不正确！")
    private String email;

    @ApiModelProperty(value = "昵称")
    @NotBlank(groups = {WebsiteUserSave.class, UserSave.class}, message = "昵称不能为空！")
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
