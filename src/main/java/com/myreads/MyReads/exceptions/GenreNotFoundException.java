package com.myreads.MyReads.exceptions;

import com.myreads.MyReads.common.BaseException;
import org.springframework.http.HttpStatus;

public class GenreNotFoundException extends BaseException {
  public GenreNotFoundException(Long id) {
    super("Genre was not found with this id: " + id, "GENRE_NOT_FOUND", HttpStatus.NOT_FOUND);
  }
}
