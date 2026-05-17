package com.myreads.MyReads.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class BookGenre {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "book_id")
  private Long bookId;

  @ManyToOne
  @JoinColumn(name = "book_id", insertable = false, updatable = false)
  private Book book;

  @Column(name = "genre_id")
  private Long genreId;

  @ManyToOne
  @JoinColumn(name = "genre_id", insertable = false, updatable = false)
  private Genre genre;

  @CreatedDate private LocalDateTime createdAt;

  @LastModifiedDate private LocalDateTime updatedAt;

  public BookGenre(Long bookId, Long genreId) {
    this.bookId = bookId;
    this.genreId = genreId;
  }

  public BookGenre() {}
}
