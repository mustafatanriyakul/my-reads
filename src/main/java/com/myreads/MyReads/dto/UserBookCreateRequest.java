package com.myreads.MyReads.dto;

import com.myreads.MyReads.models.UserBookStatus;
import lombok.Data;

@Data
public class UserBookCreateRequest {
  private Long userId;
  private Long bookId;
  private UserBookStatus status;
}
