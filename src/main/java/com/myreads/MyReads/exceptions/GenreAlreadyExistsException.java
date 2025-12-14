package com.myreads.MyReads.exceptions;

public class GenreAlreadyExistsException extends RuntimeException {
  public GenreAlreadyExistsException(String name) {
    super("Genre: " + name + "already exists.");
  }
}
