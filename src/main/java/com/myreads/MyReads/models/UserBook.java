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
public class UserBook {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long userId;
  private Long bookId;
  private LocalDate dateRead;
  private LocalDate dateAdded;

  private UserBookStatus status;

  @CreatedDate private LocalDateTime createdAt;
  @LastModifiedDate private LocalDateTime updatedAt;

  public UserBook(Long userId, Long bookId, UserBookStatus status) {
    this.userId = userId;
    this.bookId = bookId;
    this.status = status;
    this.dateAdded = LocalDate.now();
  }

  public UserBook() {}
}
