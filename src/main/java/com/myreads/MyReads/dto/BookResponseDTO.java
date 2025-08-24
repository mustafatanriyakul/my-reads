package com.myreads.MyReads.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookResponseDTO {

    private String title;

    private String authorName;

    private String isbn;

    private LocalDate datePublished;


    public BookResponseDTO(String title, String authorName, String isbn, LocalDate datePublished) {
        this.title = title;
        this.authorName = authorName;
        this.isbn = isbn;
        this.datePublished = datePublished;
    }
}
