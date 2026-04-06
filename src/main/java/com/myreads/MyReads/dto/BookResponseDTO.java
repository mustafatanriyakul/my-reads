package com.myreads.MyReads.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookResponseDTO {
  private Long id;

  private String title;

  private Long authorId;

  private String authorName;

  private String isbn;

  private LocalDate datePublished;

  public BookResponseDTO(
      Long id,
      String title,
      Long authorId,
      String authorName,
      String isbn,
      LocalDate datePublished) {
    this.id = id;
    this.title = title;
    this.authorId = authorId;
    this.authorName = authorName;
    this.isbn = isbn;
    this.datePublished = datePublished;
  }
}
