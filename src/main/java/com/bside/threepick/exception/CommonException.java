package com.bside.threepick.exception;

import com.bside.threepick.common.ErrorCode;

abstract public class CommonException extends RuntimeException{

    public CommonException(ErrorCode error, String... args) {
        super(args != null ? String.format(error.getMessage(), (Object[]) args) : error.getMessage());
        this.errorCode = error.getCode();
    }

    public int getErrorCode() {
        return errorCode;
    }
}
