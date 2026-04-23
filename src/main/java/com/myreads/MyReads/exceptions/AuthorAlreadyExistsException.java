package com.myreads.MyReads.exceptions;

import com.myreads.MyReads.common.BaseException;
import org.springframework.http.HttpStatus;

public class AuthorAlreadyExistsException extends BaseException {
  public AuthorAlreadyExistsException(String name) {
    super("Author already exists: " + name, "AUTHOR_ALREADY_EXISTS", HttpStatus.CONFLICT);
  }
}
