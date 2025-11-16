package com.myreads.MyReads.exceptions;

public class GenreNotFoundException extends RuntimeException {
  public GenreNotFoundException(Long genreId) {
    super("Genre with this id:" + genreId + " not found");
  }
}
