package com.myreads.MyReads.exceptions;

import com.myreads.MyReads.common.BaseException;
import org.springframework.http.HttpStatus;

public class AuthorNotFoundException extends BaseException {
  public AuthorNotFoundException(Long id) {
    super("Author was not found with this id: " + id, "AUTHOR_NOT_FOUND", HttpStatus.NOT_FOUND);
  }
}
