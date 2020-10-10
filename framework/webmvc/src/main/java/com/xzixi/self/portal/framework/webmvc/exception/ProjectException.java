package com.xzixi.self.portal.framework.webmvc.exception;

/**
 * 项目bug
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
