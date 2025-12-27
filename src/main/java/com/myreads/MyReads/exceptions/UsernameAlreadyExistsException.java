package com.myreads.MyReads.exceptions;

import com.myreads.MyReads.common.BaseException;
import org.springframework.http.HttpStatus;

public class UsernameAlreadyExistsException extends BaseException {
  public UsernameAlreadyExistsException(String username) {
    super("User already exists: " + username, "USER_ALREADY_EXISTS", HttpStatus.CONFLICT);
  }
}
