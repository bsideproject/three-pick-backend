package com.bside.threepick.exception;

import com.bside.threepick.common.ErrorCode;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends CommonException {

  public UnauthorizedException(ErrorCode errorCode, String message) {
    super(errorCode, message);
  }

  public UnauthorizedException(ErrorCode errorCode) {
    super(errorCode, errorCode.getMessage());
  }

  @Override
  public int getStatusCode() {
    return HttpStatus.UNAUTHORIZED.value();
  }
}
