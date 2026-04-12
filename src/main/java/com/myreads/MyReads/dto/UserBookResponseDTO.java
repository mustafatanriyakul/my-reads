package com.myreads.MyReads.dto;

import com.myreads.MyReads.models.UserBookStatus;
import jakarta.persistence.Lob;
import lombok.Data;

import java.time.LocalDate;
import java.util.Base64;

@Data
public class UserBookResponseDTO {

  private Long bookId;

  private String bookTitle;

  private Long authorId;

  private String authorName;

  private LocalDate dateRead;

  private LocalDate dateAdded;

  private UserBookStatus status;

  private String coverImageBase64;
  private String coverImageType;

  public UserBookResponseDTO(
      Long bookId,
      String bookTitle,
      Long authorId,
      String authorName,
      LocalDate dateRead,
      LocalDate dateAdded,
      UserBookStatus status,
      byte[] coverImage,
      String coverImageType) {
    this.bookId = bookId;
    this.bookTitle = bookTitle;
    this.authorId = authorId;
    this.authorName = authorName;
    this.dateRead = dateRead;
    this.dateAdded = dateAdded;
    this.status = status;
    this.coverImageType = coverImageType;

    if (coverImage != null) {
      this.coverImageBase64 = Base64.getEncoder().encodeToString(coverImage);
    }
  }
}
