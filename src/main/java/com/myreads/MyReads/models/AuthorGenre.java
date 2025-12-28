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
public class AuthorGenre {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "author_id")
  private Long authorId;

  @ManyToOne
  @JoinColumn(name = "author_id", insertable = false, updatable = false)
  private Author author;

  @Column(name = "genre_id")
  private Long genreId;

  @ManyToOne
  @JoinColumn(name = "genre_id", insertable = false, updatable = false)
  private Genre genre;

  @CreatedDate private LocalDateTime createdAt;

  @LastModifiedDate private LocalDateTime updatedAt;

  public AuthorGenre(Long authorId, Long genreId) {
    this.authorId = authorId;
    this.genreId = genreId;
  }

  public AuthorGenre() {}
}
