package com.myreads.MyReads.exceptions;

public class InvalidPasswordException extends RuntimeException {

  public InvalidPasswordException() {
    super("Password is invalid.");
  }
}
