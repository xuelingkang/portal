package com.xzixi.self.portal.framework.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 客户端原因产生的异常
 *
 * @author 薛凌康
 */
@Getter
@Setter
public class ClientException extends RuntimeException {

    private int status;

    public ClientException(int status, String message) {
        super(message);
        this.status = status;
    }
}
