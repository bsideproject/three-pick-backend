package com.bside.threepick.exception;

import org.springframework.http.HttpStatus;

public class AlreadyExistsEmailException extends CommonException {

  public AlreadyExistsEmailException(String message) {
    super(message);
  }

  @Override
  public int getStatusCode() {
    return HttpStatus.BAD_REQUEST.value();
  }
}
