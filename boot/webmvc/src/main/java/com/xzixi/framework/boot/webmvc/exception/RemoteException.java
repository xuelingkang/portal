package com.xzixi.framework.boot.webmvc.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 调用远程接口异常
 *
 * @author xuelingkang
 * @date 2020-10-26
 */
@Getter
@Setter
public class RemoteException extends RuntimeException {

    private int status;

    public RemoteException(int status, String message) {
        super(message);
        this.status = status;
    }
}
