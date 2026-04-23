package com.myreads.MyReads.controllers;

import com.myreads.MyReads.common.ControllerResponse;
import com.myreads.MyReads.dto.BookCreateRequest;
import com.myreads.MyReads.services.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/books")
public class BookController {
  public static final String BOOK_CREATED_MESSAGE = "Book created.";
  public static final String ALL_BOOKS_FETCHED_MESSAGE = "All books fetched.";
  public static final String BOOK_FETCHED_MESSAGE = "Book fetched.";
  private final BookService bookService;

  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @PostMapping("/create")
  public ResponseEntity<ControllerResponse<String>> create(
      @RequestBody BookCreateRequest bookCreateRequest) {

    bookService.createBook(bookCreateRequest);

    return ResponseEntity.ok(ControllerResponse.success(BOOK_CREATED_MESSAGE));
  }

  @GetMapping("/all")
  public ResponseEntity<ControllerResponse<?>> getAll() {
    return ResponseEntity.ok(
        ControllerResponse.success(ALL_BOOKS_FETCHED_MESSAGE, bookService.getAllBooks()));
  }

  @GetMapping("/{bookId}")
  public ResponseEntity<ControllerResponse<?>> getBookDetailsByBookId(@PathVariable Long bookId) {
    return ResponseEntity.ok(
        ControllerResponse.success(
            BOOK_FETCHED_MESSAGE, bookService.getBookDetailsByBookId(bookId)));
  }

  @PostMapping("/{bookId}/cover")
  public ResponseEntity<ControllerResponse<?>> uploadBookCoverImage(
      @PathVariable Long bookId, @RequestParam("file") MultipartFile file) throws IOException {

    bookService.uploadBookCoverImage(bookId, file);
    return ResponseEntity.ok(ControllerResponse.success("Cover image uploaded."));
  }
}
