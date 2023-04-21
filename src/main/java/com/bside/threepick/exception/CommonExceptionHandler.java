package com.bside.threepick.exception;

import com.bside.threepick.common.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class CommonExceptionHandler {

  @ExceptionHandler({CommonException.class})
  public ResponseEntity<ErrorMessage> common(CommonException ex) {
    return ResponseEntity.status(ex.getStatusCode())
        .body(new ErrorMessage(ex.getMessage(), ex.getErrorCode(), ex.getStatusCode()));
  }

  @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
  public ResponseEntity<ErrorMessage> enumValid(RuntimeException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ErrorMessage("요청에 잘못 된 값이 포함되어 있어요.", ErrorCode.BAD_REQUEST.getCode(),
            HttpStatus.BAD_REQUEST.value()));
  }

  @ExceptionHandler({MethodArgumentNotValidException.class})
  public ResponseEntity<ErrorMessage> methodArgumentNotValid(MethodArgumentNotValidException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ErrorMessage(ex.getFieldError().getDefaultMessage(), ErrorCode.BAD_REQUEST.getCode(),
            HttpStatus.BAD_REQUEST.value()));
  }

  @AllArgsConstructor
  @Data
  private class ErrorMessage {

    private String message;
    private int code;
    private int statusCode;
  }
}
