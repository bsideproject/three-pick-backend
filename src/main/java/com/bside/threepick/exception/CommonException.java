package com.bside.threepick.exception;

import com.bside.threepick.common.ErrorCode;

abstract public class CommonException extends RuntimeException{

    public int errorCode;
    public abstract int getStatusCode();


    public CommonException(ErrorCode error, String message) {
        super(message);
        this.errorCode = error.getCode();
    }
}
