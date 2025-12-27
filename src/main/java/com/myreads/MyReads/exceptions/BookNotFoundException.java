package com.myreads.MyReads.exceptions;

import com.myreads.MyReads.common.BaseException;
import org.springframework.http.HttpStatus;

public class BookNotFoundException extends BaseException {
  public BookNotFoundException(Long id) {
    super("Book was not found with this id: " + id, "BOOK_NOT_FOUND", HttpStatus.NOT_FOUND);
  }
}
