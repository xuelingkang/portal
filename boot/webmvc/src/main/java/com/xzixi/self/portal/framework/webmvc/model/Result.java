package com.xzixi.self.portal.framework.webmvc.model;

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
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 状态码 */
    private int code = 200;

    /** 消息 */
    private String message;

    /** 数据 */
    private T data;

    public Result(T data) {
        this.data = data;
    }
}
