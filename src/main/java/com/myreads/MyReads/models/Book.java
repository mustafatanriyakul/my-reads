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
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Long authorId;

    private String isbn;

    private LocalDate datePublished;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Book(String title, Long authorId, String isbn, LocalDate datePublished) {
        this.title = title;
        this.authorId = authorId;
        this.isbn = isbn;
        this.datePublished = datePublished;
    }


    public Book() {

    }
}
