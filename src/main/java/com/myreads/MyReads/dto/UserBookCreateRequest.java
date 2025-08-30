package com.myreads.MyReads.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserBookCreateRequest {
    private Long userId;
    private Long bookId;
    private LocalDate dateRead;
}
