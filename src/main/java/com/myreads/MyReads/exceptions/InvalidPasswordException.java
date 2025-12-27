package com.myreads.MyReads.exceptions;

import com.myreads.MyReads.common.BaseException;
import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends BaseException {

  public InvalidPasswordException() {
    super("Password is invalid", "INVALID_PASSWORD", HttpStatus.BAD_REQUEST);
  }
}
