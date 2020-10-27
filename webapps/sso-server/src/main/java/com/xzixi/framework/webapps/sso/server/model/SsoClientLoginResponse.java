package com.xzixi.framework.webapps.sso.server.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * sso-client登录成功响应
 *
 * @author xuelingkang
 * @date 2020-10-27
 */
@Data
@ApiModel(description = "sso-client登录成功响应")
public class SsoClientLoginResponse {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "x-access-token")
    private String accessToken;

    @ApiModelProperty(value = "x-refresh-token")
    private String refreshToken;

    @ApiModelProperty(value = "登录时间")
    private Long loginTime;

    @ApiModelProperty(value = "x-access-token失效时间")
    private Long accessExpireTime;

    @ApiModelProperty(value = "x-refresh-token失效时间")
    private Long refreshExpireTime;
}
