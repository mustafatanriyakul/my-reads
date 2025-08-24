package com.myreads.MyReads.repositories;

import com.myreads.MyReads.models.MyBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyBookRepository extends JpaRepository<MyBook, Long> {
    List<MyBook> findByUserId(Long userId);

    boolean existsByUserIdAndBookId(Long userId, Long bookId);
}
