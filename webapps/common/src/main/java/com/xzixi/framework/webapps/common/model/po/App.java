package com.xzixi.framework.webapps.common.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xzixi.framework.boot.webmvc.model.BaseModel;
import com.xzixi.framework.webapps.common.model.valid.AppSave;
import com.xzixi.framework.webapps.common.model.valid.AppUpdate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * 应用
 *
 * @author xuelingkang
 * @date 2020-10-25
 */
@Data
@TableName(value = "t_app", autoResultMap = true)
@ApiModel(value="App对象", description="应用")
public class App extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "应用id")
    @TableId(value = "id", type = IdType.AUTO)
    @Null(groups = {AppSave.class}, message = "应用id必须为空！")
    @NotNull(groups = {AppUpdate.class}, message = "应用id不能为空！")
    private Integer id;

    @ApiModelProperty(value = "应用标识")
    @NotNull(groups = {AppSave.class, AppUpdate.class}, message = "应用标识不能为空！")
    @Length(groups = {AppSave.class, AppUpdate.class}, max = 20, message = "应用标识不能超过20字！")
    private String uid;

    @ApiModelProperty(value = "密钥")
    @NotNull(groups = {AppSave.class, AppUpdate.class}, message = "密钥不能为空！")
    @Length(groups = {AppSave.class, AppUpdate.class}, max = 32, message = "密钥不能超过64！")
    private String secret;

    @ApiModelProperty(value = "应用名称")
    @NotNull(groups = {AppSave.class, AppUpdate.class}, message = "应用名称不能为空！")
    @Length(groups = {AppSave.class, AppUpdate.class}, max = 20, message = "应用名称不能超过20字！")
    private String name;

    @ApiModelProperty(value = "登录回调url")
    @Length(groups = {AppSave.class, AppUpdate.class}, max = 255, message = "登录回调url不能超过255字！")
    private String loginCallbackUrl;

    @ApiModelProperty(value = "登出回调url")
    @Length(groups = {AppSave.class, AppUpdate.class}, max = 255, message = "登出回调url不能超过255字！")
    private String logoutCallbackUrl;

    @ApiModelProperty(value = "顺序")
    @NotNull(groups = {AppSave.class, AppUpdate.class}, message = "顺序不能为空！")
    private Integer seq;
}
