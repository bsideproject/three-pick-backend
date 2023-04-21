package com.bside.threepick.exception;

import com.bside.threepick.common.ErrorCode;
import org.springframework.http.HttpStatus;

public class EntityAlreadyExistsException extends CommonException {

  public EntityAlreadyExistsException(ErrorCode code, String message) {
    super(code, message);

  }

  @Override
  public int getStatusCode() {
    return HttpStatus.BAD_REQUEST.value();
  }
}
