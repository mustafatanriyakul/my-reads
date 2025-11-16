package com.myreads.MyReads.exceptions;

public class AuthorGenreAlreadyExistsException extends RuntimeException {
  public AuthorGenreAlreadyExistsException() {
    super("This AuthorGenre already exists.");
  }
}
