package com.myreads.MyReads.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookCreateRequest {

    private String title;

    private Long authorId;

    private String isbn;

    private LocalDate datePublished;
}
