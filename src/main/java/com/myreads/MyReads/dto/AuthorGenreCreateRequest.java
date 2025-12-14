package com.myreads.MyReads.dto;

import lombok.Data;

@Data
public class AuthorGenreCreateRequest {

  private Long authorId;

  private Long genreId;
}
