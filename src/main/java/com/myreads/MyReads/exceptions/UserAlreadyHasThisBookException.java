package com.myreads.MyReads.exceptions;

public class UserAlreadyHasThisBookException extends RuntimeException {
  public UserAlreadyHasThisBookException(Long bookId) {
    super("User already has this book in MyBooks '" + bookId + "'.");
  }
}
