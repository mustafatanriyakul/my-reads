package com.myreads.MyReads.exceptions;

import com.myreads.MyReads.common.BaseException;
import org.springframework.http.HttpStatus;

public class UserAlreadyHasThisBookException extends BaseException {
  public UserAlreadyHasThisBookException(Long bookId) {
    super(
        "User already has this book in MyBooks: '" + bookId,
        "USER_ALREADY_HAS_BOOK",
        HttpStatus.CONFLICT);
  }
}
