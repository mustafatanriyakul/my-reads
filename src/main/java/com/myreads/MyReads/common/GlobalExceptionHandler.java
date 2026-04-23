package com.myreads.MyReads.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BaseException.class)
  public ResponseEntity<ControllerResponse<?>> handleBaseException(BaseException ex) {
    ControllerResponse<?> response =
        ControllerResponse.error(ex.getMessage(), ex.getErrorCode(), List.of(ex.getMessage()));
    return ResponseEntity.status(ex.getHttpStatus()).body(response);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ControllerResponse<?>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, String> fieldErrors = new HashMap<>();

    ex.getBindingResult()
        .getFieldErrors()
        .forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));

    ControllerResponse<String> response = new ControllerResponse<>();
    response.setSuccess(false);
    response.setMessage("Validation failed");
    response.setErrorCode("VALIDATION_ERROR");
    response.setFieldErrors(fieldErrors);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }
}
