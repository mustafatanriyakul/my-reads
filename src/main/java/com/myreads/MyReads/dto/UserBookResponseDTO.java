package com.myreads.MyReads.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserBookResponseDTO {

    private String bookTitle;

    private String authorName;

    private LocalDate dateRead;

    private LocalDate dateAdded;


    public UserBookResponseDTO(String bookTitle, String authorName, LocalDate dateRead, LocalDate dateAdded) {
        this.bookTitle = bookTitle;
        this.authorName = authorName;
        this.dateRead = dateRead;
        this.dateAdded = dateAdded;
    }
}






