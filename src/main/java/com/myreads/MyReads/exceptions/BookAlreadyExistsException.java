package com.myreads.MyReads.exceptions;

import com.myreads.MyReads.common.BaseException;
import org.springframework.http.HttpStatus;

public class BookAlreadyExistsException extends BaseException {
  public BookAlreadyExistsException(String title) {
    super("Book already exists: " + title, "BOOK_ALREADY_EXISTS", HttpStatus.CONFLICT);
  }
}
