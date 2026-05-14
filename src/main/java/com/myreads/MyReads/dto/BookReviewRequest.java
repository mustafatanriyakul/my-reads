package com.myreads.MyReads.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookReviewRequest {
  private Long bookId;
  private LocalDate dateStarted;
  private LocalDate dateFinished;
}
