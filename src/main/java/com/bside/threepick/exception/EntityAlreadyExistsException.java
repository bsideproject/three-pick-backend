package com.bside.threepick.exception;

import org.springframework.http.HttpStatus;

public class EntityAlreadyExistsException extends CommonException {

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
