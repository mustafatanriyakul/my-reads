package com.myreads.MyReads.exceptions;

public class InvalidUsernameException extends RuntimeException {

  public InvalidUsernameException(String username) {
    super("Username '" + username + "' is invalid.");
  }
}
