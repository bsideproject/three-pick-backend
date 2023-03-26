package com.bside.threepick.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {

  @ExceptionHandler(CommonException.class)
  public ResponseEntity<ErrorMessage> common(CommonException ex) {
    return ResponseEntity.status(ex.getStatusCode())
        .body(new ErrorMessage(ex.getMessage(), ex.getStatusCode()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorMessage> methodArgumentNotValid(MethodArgumentNotValidException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ErrorMessage(ex.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST.value()));
  }

  @AllArgsConstructor
  @Data
  private class ErrorMessage {

    private String message;
    private int statusCode;
  }
}
