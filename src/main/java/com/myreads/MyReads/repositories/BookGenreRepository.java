package com.myreads.MyReads.repositories;

import com.myreads.MyReads.models.AuthorGenre;
import com.myreads.MyReads.models.BookGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookGenreRepository extends JpaRepository<BookGenre, Long> {

  boolean existsByBookIdAndGenreId(Long bookId, Long genreId);

  @Query(
      "SELECT g.name "
          + "FROM BookGenre bg "
          + "JOIN Genre g ON bg.genreId = g.id "
          + "WHERE bg.bookId = :bookId")
  List<String> findGenreNamesByBookId(Long bookId);
}
