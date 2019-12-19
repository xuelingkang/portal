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
@ApiModel(description = "返回结果")
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "状态码")
    private int code = 200;
    @ApiModelProperty(value = "消息")
    private String message;
    @ApiModelProperty(value = "数据")
    private T data;
}
