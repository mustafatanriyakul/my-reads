package com.myreads.MyReads.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookResponseDTO {
  private Long id;

  private String title;

  private String authorName;

  private String isbn;

  private LocalDate datePublished;

  private Long authorId;

  public BookResponseDTO(
      Long id,
      String title,
      String authorName,
      String isbn,
      LocalDate datePublished,
      Long authorId) {
    this.id = id;
    this.title = title;
    this.authorName = authorName;
    this.isbn = isbn;
    this.datePublished = datePublished;
    this.authorId = authorId;
  }
}
