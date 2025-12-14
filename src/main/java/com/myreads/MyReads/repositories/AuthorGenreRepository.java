package com.myreads.MyReads.repositories;

import com.myreads.MyReads.models.AuthorGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorGenreRepository extends JpaRepository<AuthorGenre, Long> {

  boolean existsByAuthorIdAndGenreId(Long authorId, Long genreId);

  @Query(
      "SELECT g.name "
          + "FROM AuthorGenre ag "
          + "JOIN Genre g ON ag.genreId = g.id "
          + "WHERE ag.authorId = :authorId")
  List<String> findGenreNamesByAuthorId(Long authorId);
}
