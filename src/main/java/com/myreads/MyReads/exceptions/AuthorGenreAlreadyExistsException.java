package com.myreads.MyReads.exceptions;

import com.myreads.MyReads.common.BaseException;
import org.springframework.http.HttpStatus;

public class AuthorGenreAlreadyExistsException extends BaseException {
  public AuthorGenreAlreadyExistsException() {
    super("AuthorGenre already exists: ", "AUTHOR_GENRE_ALREADY_EXISTS", HttpStatus.CONFLICT);
  }
}
