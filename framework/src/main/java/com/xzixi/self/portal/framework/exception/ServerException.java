package com.xzixi.self.portal.framework.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 服务端原因产生的异常
 *
 * @author 薛凌康
 */
@Getter
@Setter
public class ServerException extends RuntimeException {

    private Object data;

    public ServerException(String message) {
        super(message);
    }

    public ServerException(Object data, String message) {
        super(message);
        this.data = data;
    }

    public ServerException(Object data, String message, Throwable cause) {
        super(message, cause);
        this.data = data;
    }
}
