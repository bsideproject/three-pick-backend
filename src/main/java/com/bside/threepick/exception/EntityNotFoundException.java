package com.bside.threepick.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends CommonException {

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
