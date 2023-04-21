package com.bside.threepick.exception;

import org.springframework.http.HttpStatus;

public class ChangeTimeValueCountException extends CommonException {

  public ChangeTimeValueCountException(String message) {
    super(message);
  }

  @Override
  public int getStatusCode() {
    return HttpStatus.BAD_REQUEST.value();
  }
}
