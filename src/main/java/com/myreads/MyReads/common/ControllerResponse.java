package com.myreads.MyReads.common;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ControllerResponse<T> {
  private boolean success;
  private String message;
  private T body;

  private String errorCode;
  private List<String> errors;
  private Map<String, String> fieldErrors;

  public ControllerResponse() {}

  public static <T> ControllerResponse<T> success(String message) {
    ControllerResponse<T> response = new ControllerResponse<>();
    response.success = true;
    response.message = message;
    response.body = null;
    return response;
  }

  public static <T> ControllerResponse<T> success(String message, T body) {
    ControllerResponse<T> response = new ControllerResponse<>();
    response.success = true;
    response.message = message;
    response.body = body;
    return response;
  }

  public static <T> ControllerResponse<T> error(
      String message, String errorCode, List<String> errors) {
    ControllerResponse<T> response = new ControllerResponse<>();
    response.success = false;
    response.message = message;
    response.errorCode = errorCode;
    response.errors = errors;
    return response;
  }
}
