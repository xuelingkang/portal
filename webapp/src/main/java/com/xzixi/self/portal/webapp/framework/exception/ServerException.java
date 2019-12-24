package com.xzixi.self.portal.webapp.framework.exception;

/**
 * @author 薛凌康
 */
public class ServerException extends RuntimeException {

    public ServerException() {
        super("服务器异常！");
    }

    public ServerException(String message) {
        super(message);
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerException(Throwable cause) {
        super(cause);
    }

    public ServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
