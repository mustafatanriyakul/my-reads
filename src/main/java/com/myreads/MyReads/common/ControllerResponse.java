package com.myreads.MyReads.common;

import lombok.Data;

@Data
public class ControllerResponse<T> {
  private String message;
  private T body;

  public ControllerResponse(String message) {
    this.message = message;
  }

  public ControllerResponse(T body) {
    this.body = body;
  }

  public ControllerResponse(String message, T body) {
    this.message = message;
    this.body = body;
  }
}
