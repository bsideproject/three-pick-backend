package com.bside.threepick.exception;

import org.springframework.http.HttpStatus;

public class IllegalRequestException extends CommonException {
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    public IllegalRequestException(String message) {
        super(message);
    }
}
