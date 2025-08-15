package com.myreads.MyReads.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookResponseDTO {

    private String title;

    private String authorName;

    private String isbn;

    private LocalDate datePublished;
}
