package com.bside.threepick.exception;

import com.bside.threepick.common.ErrorCode;
import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends CommonException {

  public EntityNotFoundException(String message) {
    super(message);
  }

  @Override
  public int getStatusCode() {
    return HttpStatus.NOT_FOUND.value();
  }
}
