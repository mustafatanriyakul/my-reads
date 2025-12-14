package com.myreads.MyReads.dto;

import lombok.Data;

import java.util.List;

@Data
public class AuthorResponseDTO {

  private String authorName;

  private String birthplace;

  private List<String> genres;

  public AuthorResponseDTO(String authorName, String birthplace, List<String> genres) {
    this.authorName = authorName;
    this.birthplace = birthplace;
    this.genres = genres;
  }
}
