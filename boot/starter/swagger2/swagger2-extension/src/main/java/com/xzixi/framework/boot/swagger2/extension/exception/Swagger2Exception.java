package com.xzixi.framework.boot.swagger2.extension.exception;

public class Swagger2Exception extends RuntimeException {

    public Swagger2Exception(String message) {
        super(message);
    }

    public Swagger2Exception(String message, Throwable cause) {
        super(message, cause);
    }
}
