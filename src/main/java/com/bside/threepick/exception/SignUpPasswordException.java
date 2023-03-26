package com.bside.threepick.exception;

import org.springframework.http.HttpStatus;

public class SignUpPasswordException extends CommonException {

  public SignUpPasswordException(String message) {
    super(message);
  }

  @Override
  public int getStatusCode() {
    return HttpStatus.BAD_REQUEST.value();
  }
}
