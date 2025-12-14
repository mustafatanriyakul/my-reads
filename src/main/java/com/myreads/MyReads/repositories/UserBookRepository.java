package com.myreads.MyReads.repositories;

import com.myreads.MyReads.models.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBookRepository extends JpaRepository<UserBook, Long> {
  List<UserBook> findByUserId(Long userId);

  boolean existsByUserIdAndBookId(Long userId, Long bookId);
}
