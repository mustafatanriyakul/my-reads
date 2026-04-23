package com.myreads.MyReads.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Base64;

@Data
public class BookResponseDTO {
  private Long id;

  private String title;

  private Long authorId;

  private String authorName;

  private String isbn;

  private LocalDate datePublished;

  private String coverImageBase64;
  private String coverImageType;

  public BookResponseDTO(
      Long id,
      String title,
      Long authorId,
      String authorName,
      String isbn,
      LocalDate datePublished,
      byte[] coverImage,
      String coverImageType) {
    this.id = id;
    this.title = title;
    this.authorId = authorId;
    this.authorName = authorName;
    this.isbn = isbn;
    this.datePublished = datePublished;
    this.coverImageType = coverImageType;

    if (coverImage != null) {
      this.coverImageBase64 = Base64.getEncoder().encodeToString(coverImage);
    }
  }
}
