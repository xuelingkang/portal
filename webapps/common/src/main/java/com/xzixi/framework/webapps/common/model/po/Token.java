package com.xzixi.framework.webapps.common.model.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "令牌")
public class Token implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标识")
    private String signature;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "登陆时间")
    private Long loginTime;

    @ApiModelProperty(value = "过期时间")
    private Long expireTime;
}
