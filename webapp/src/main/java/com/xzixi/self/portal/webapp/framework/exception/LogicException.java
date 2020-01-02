package com.xzixi.self.portal.webapp.framework.exception;

import lombok.Data;

/**
 * 客户端原因产生的异常
 *
 * @author 薛凌康
 */
@Data
public class LogicException extends RuntimeException {

    private int status;

    public LogicException(int status) {
        super();
        this.status = status;
    }

    public LogicException(int status, String message) {
        super(message);
        this.status = status;
    }

    public LogicException(int status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public LogicException(int status, Throwable cause) {
        super(cause);
        this.status = status;
    }

    protected LogicException(int status, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.status = status;
    }
}
