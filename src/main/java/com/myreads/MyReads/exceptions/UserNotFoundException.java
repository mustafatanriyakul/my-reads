package com.myreads.MyReads.exceptions;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(Long id) {
    super("User was not found with this id '" + id + "'.");
  }
}
