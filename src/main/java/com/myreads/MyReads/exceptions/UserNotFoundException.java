package com.myreads.MyReads.exceptions;

import com.myreads.MyReads.common.BaseException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException {
  public UserNotFoundException(Long id) {
    super("User was not found with this id: " + id, "USER_NOT_FOUND", HttpStatus.NOT_FOUND);
  }
}
