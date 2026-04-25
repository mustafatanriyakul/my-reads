package com.myreads.MyReads.exceptions;

import com.myreads.MyReads.common.BaseException;
import org.springframework.http.HttpStatus;

public class InvalidReviewDateException extends BaseException {

  public InvalidReviewDateException() {
    super("Date is invalid: ", "INVALID_DATE", HttpStatus.BAD_REQUEST);
  }
}
