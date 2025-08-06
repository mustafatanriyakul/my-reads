package com.myreads.MyReads.requests;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MyBookCreateRequest {
    private Long userId;
    private Long bookId;
    private LocalDate dateRead;
    private LocalDate dateAdded;
}
