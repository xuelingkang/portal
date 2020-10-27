package com.xzixi.framework.webapps.sso.server.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * sso-server登录成功响应
 *
 * @author xuelingkang
 * @date 2020-10-27
 */
@Data
@ApiModel(description = "sso-server登录成功响应")
public class SsoServerLoginResponse {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "sso站点的x-access-token")
    private String ssoAccessToken;

    @ApiModelProperty(value = "app站点的x-access-token")
    private String appAccessToken;

    @ApiModelProperty(value = "通用的x-refresh-token")
    private String refreshToken;

    @ApiModelProperty(value = "登录时间")
    private Long loginTime;

    @ApiModelProperty(value = "x-access-token失效时间")
    private Long accessExpireTime;

    @ApiModelProperty(value = "x-refresh-token失效时间")
    private Long refreshExpireTime;

    @ApiModelProperty(value = "跳转url")
    private String redirectUrl;
}
