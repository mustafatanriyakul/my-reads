package com.myreads.MyReads.repositories;

import com.myreads.MyReads.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {

  boolean existsByName(String name);
}
