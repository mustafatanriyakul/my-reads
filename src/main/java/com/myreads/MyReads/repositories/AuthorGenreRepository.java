package com.myreads.MyReads.repositories;

import com.myreads.MyReads.models.AuthorGenre;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthorGenreRepository extends JpaRepository<AuthorGenre, Long> {

    boolean existsByAuthorIdAndGenreId(Long authorId, Long genreId);
}
