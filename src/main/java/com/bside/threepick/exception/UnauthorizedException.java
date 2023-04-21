package com.bside.threepick.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends CommonException {

  public UnauthorizedException(String message) {
    super(message);
  }

  @Override
  public int getStatusCode() {
    return HttpStatus.UNAUTHORIZED.value();
  }
}
