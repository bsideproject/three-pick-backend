package com.bside.threepick.exception;

abstract public class CommonException extends RuntimeException{

    public abstract int getStatusCode();

    public CommonException(String message) {
        super(message);
    }
}
