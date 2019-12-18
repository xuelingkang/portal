package com.xzixi.self.portal.webapp.framework.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 返回结果
 *
 * @author 薛凌康
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel("返回结果")
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("状态码")
    private int code = 200;
    @ApiModelProperty("消息")
    private String message;
    @ApiModelProperty("数据")
    private T data;
}
