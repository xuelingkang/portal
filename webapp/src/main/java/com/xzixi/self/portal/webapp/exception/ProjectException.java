package com.xzixi.self.portal.webapp.exception;

/**
 * 项目异常
 *
 * @author 薛凌康
 */
public class ProjectException extends RuntimeException {

    public ProjectException(String message) {
        super(message);
    }

    public ProjectException(String message, Throwable cause) {
        super(message, cause);
    }
}
