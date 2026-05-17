package com.myreads.MyReads.exceptions;

import com.myreads.MyReads.common.BaseException;
import org.springframework.http.HttpStatus;

public class BookGenreAlreadyExistsException extends BaseException {
  public BookGenreAlreadyExistsException() {
    super("BookGenre already exists: ", "BOOK_GENRE_ALREADY_EXISTS", HttpStatus.CONFLICT);
  }
}
