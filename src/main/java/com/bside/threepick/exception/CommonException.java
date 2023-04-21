package com.bside.threepick.exception;

import com.bside.threepick.common.ErrorCode;

abstract public class CommonException extends RuntimeException {

  private int errorCode;

  public CommonException(ErrorCode error, String message) {
    super(message);
    this.errorCode = error.getCode();
  }

  public abstract int getStatusCode();

  public int getErrorCode() {
    return errorCode;
  }
}
