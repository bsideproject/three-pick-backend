package com.bside.threepick.exception;

import com.bside.threepick.common.ErrorCode;
import org.springframework.http.HttpStatus;

public class ChangeTimeValueCountException extends CommonException {

  public ChangeTimeValueCountException(ErrorCode errorCode) {
    super(errorCode, errorCode.getMessage());
  }

  @Override
  public int getStatusCode() {
    return HttpStatus.BAD_REQUEST.value();
  }
}
