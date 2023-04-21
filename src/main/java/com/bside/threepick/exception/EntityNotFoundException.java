package com.bside.threepick.exception;

import com.bside.threepick.common.ErrorCode;
import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends CommonException {

  public EntityNotFoundException(ErrorCode errorCode, String message) {
    super(errorCode, message);
  }

  @Override
  public int getStatusCode() {
    return HttpStatus.NOT_FOUND.value();
  }
}
