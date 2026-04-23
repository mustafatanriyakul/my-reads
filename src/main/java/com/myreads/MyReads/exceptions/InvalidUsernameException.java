package com.myreads.MyReads.exceptions;

import com.myreads.MyReads.common.BaseException;
import org.springframework.http.HttpStatus;

public class InvalidUsernameException extends BaseException {

  public InvalidUsernameException(String username) {
    super("Username is invalid: " + username, "INVALID_USERNAME", HttpStatus.BAD_REQUEST);
  }
}
