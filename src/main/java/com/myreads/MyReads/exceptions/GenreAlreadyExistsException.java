package com.myreads.MyReads.exceptions;

import com.myreads.MyReads.common.BaseException;
import org.springframework.http.HttpStatus;

public class GenreAlreadyExistsException extends BaseException {
  public GenreAlreadyExistsException(String name) {
    super("Genre already exists: " + name, "AUTHOR_ALREADY_EXISTS", HttpStatus.CONFLICT);
  }
}
