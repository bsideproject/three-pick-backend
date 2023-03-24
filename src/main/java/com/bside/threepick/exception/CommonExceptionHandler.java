package com.bside.threepick.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {

  @ExceptionHandler(CommonException.class)
  public ResponseEntity<ErrorMessage> common(CommonException ex) {
    return ResponseEntity.status(ex.getStatusCode())
        .body(new ErrorMessage(ex.getMessage(), ex.getStatusCode()));
  }

  @AllArgsConstructor
  @Data
  private class ErrorMessage {

    private String message;
    private int statusCode;
  }
}
