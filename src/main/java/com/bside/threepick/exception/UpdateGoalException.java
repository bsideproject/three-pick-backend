package com.bside.threepick.exception;

import com.bside.threepick.common.ErrorCode;
import org.springframework.http.HttpStatus;

public class UpdateGoalException extends CommonException {

  public UpdateGoalException(ErrorCode errorCode) {
    super(errorCode, errorCode.getMessage());
  }

  public UpdateGoalException(ErrorCode errorCode, String message) {
    super(errorCode, message);
  }

  @Override
  public int getStatusCode() {
    return HttpStatus.BAD_REQUEST.value();
  }
}
