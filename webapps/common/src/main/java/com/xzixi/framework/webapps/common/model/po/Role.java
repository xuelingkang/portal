package com.xzixi.framework.webapps.common.model.po;

import com.xzixi.framework.boot.webmvc.model.BaseModel;
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
