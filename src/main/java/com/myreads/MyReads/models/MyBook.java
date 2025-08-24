package com.myreads.MyReads.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class MyBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long bookId;
    private LocalDate dateRead;
    private LocalDate dateAdded;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public MyBook(Long userId, Long bookId, LocalDate dateRead) {
        this.userId = userId;
        this.bookId = bookId;
        this.dateRead = dateRead;
        this.dateAdded = LocalDate.now();
    }

    public MyBook() {

    }
}
