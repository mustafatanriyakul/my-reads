package com.myreads.MyReads.dto;

import com.myreads.MyReads.models.UserBookStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserBookResponseDTO {

  private Long bookId;

  private String bookTitle;

  private Long authorId;

  private String authorName;

  private LocalDate dateRead;

  private LocalDate dateAdded;

  private UserBookStatus status;

  public UserBookResponseDTO(
      Long bookId,
      String bookTitle,
      Long authorId,
      String authorName,
      LocalDate dateRead,
      LocalDate dateAdded,
      UserBookStatus status) {
    this.bookId = bookId;
    this.bookTitle = bookTitle;
    this.authorId = authorId;
    this.authorName = authorName;
    this.dateRead = dateRead;
    this.dateAdded = dateAdded;

    this.status = status;
  }
}
