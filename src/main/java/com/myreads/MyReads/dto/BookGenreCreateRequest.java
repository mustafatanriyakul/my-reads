package com.myreads.MyReads.dto;

import lombok.Data;

@Data
public class BookGenreCreateRequest {

  private Long bookId;

  private Long genreId;
}
