package com.xzixi.self.portal.sftp.pool.exception;

/**
 * sftp连接池异常
 *
 * @author 薛凌康
 */
public class SftpPoolException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SftpPoolException() {
    }

    public SftpPoolException(String message) {
        super(message);
    }

    public SftpPoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public SftpPoolException(Throwable cause) {
        super(cause);
    }

    public SftpPoolException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
