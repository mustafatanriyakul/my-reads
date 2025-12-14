package com.myreads.MyReads.dto;

import com.myreads.MyReads.models.UserBookStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserBookResponseDTO {

  private String bookTitle;

  private String authorName;

  private LocalDate dateRead;

  private LocalDate dateAdded;

  private Long authorId;

  private UserBookStatus status;

  public UserBookResponseDTO(
      String bookTitle,
      String authorName,
      LocalDate dateRead,
      LocalDate dateAdded,
      Long authorId,
      UserBookStatus status) {
    this.bookTitle = bookTitle;
    this.authorName = authorName;
    this.dateRead = dateRead;
    this.dateAdded = dateAdded;
    this.authorId = authorId;
    this.status = status;
  }
}
