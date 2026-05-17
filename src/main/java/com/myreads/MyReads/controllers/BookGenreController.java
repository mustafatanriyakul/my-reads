package com.myreads.MyReads.controllers;

import com.myreads.MyReads.common.ControllerResponse;
import com.myreads.MyReads.dto.BookGenreCreateRequest;
import com.myreads.MyReads.services.BookGenreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("book_genre")
public class BookGenreController {

  public static final String BOOK_GENRE_CREATED_MESSAGE = "BookGenre created.";
  public static final String BOOK_GENRE_FETCHED_MESSAGE = "BookGenre fetched.";
  public static final String ALL_BOOK_GENRE_FETCHED_MESSAGE = "All BookGenres fetched.";

  private final BookGenreService BookGenreService;

  public BookGenreController(BookGenreService BookGenreService) {
    this.BookGenreService = BookGenreService;
  }

  @PostMapping("/create")
  public ResponseEntity<ControllerResponse<?>> createBookGenre(
      @RequestBody BookGenreCreateRequest BookGenreCreateRequest) {

    BookGenreService.createBookGenre(BookGenreCreateRequest);

    return ResponseEntity.ok(ControllerResponse.success(BOOK_GENRE_CREATED_MESSAGE));
  }

  @GetMapping("/all")
  public ResponseEntity<ControllerResponse<?>> getAllBookGenres() {

    return ResponseEntity.ok(
        ControllerResponse.success(
            ALL_BOOK_GENRE_FETCHED_MESSAGE, BookGenreService.getAllBookGenres()));
  }

  @GetMapping("/{bookId}")
  public ResponseEntity<ControllerResponse<?>> getBookGenresByBookId(@PathVariable Long bookId) {

    return ResponseEntity.ok(
        ControllerResponse.success(
            BOOK_GENRE_FETCHED_MESSAGE, BookGenreService.getBookGenreByBookId(bookId)));
  }
}
