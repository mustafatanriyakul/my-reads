package com.myreads.MyReads.controllers;

import com.myreads.MyReads.common.ControllerResponse;
import com.myreads.MyReads.dto.AuthorResponseDTO;
import com.myreads.MyReads.dto.BookResponseDTO;
import com.myreads.MyReads.dto.AuthorCreateRequest;
import com.myreads.MyReads.services.AuthorService;
import com.myreads.MyReads.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {
  public static final String AUTHOR_CREATED_MESSAGE = "Author created.";
  public static final String ALL_AUTHORS_FETCHED_MESSAGE = "All authors fetched.";
  public static final String AUTHOR_BOOKS_FETCHED_MESSAGE = "Author's books fetched.";
  public static final String AUTHOR_DETAILS_FETCHED_MESSAGE = "Author's details fetched.";
  private final AuthorService authorService;
  private final UserService userService;

  public AuthorController(AuthorService authorService, UserService userService) {
    this.authorService = authorService;
    this.userService = userService;
  }

  @PostMapping("/create")
  public ResponseEntity<ControllerResponse<String>> create(
      @RequestBody AuthorCreateRequest authorCreateRequest) {

    authorService.create(authorCreateRequest);

    return ResponseEntity.ok(ControllerResponse.success(AUTHOR_CREATED_MESSAGE));
  }

  @GetMapping("/all")
  public ResponseEntity<ControllerResponse<?>> gelAll() {

    return ResponseEntity.ok(
        ControllerResponse.success(ALL_AUTHORS_FETCHED_MESSAGE, authorService.getAll()));
  }

  @GetMapping("/{authorId}/books")
  public ResponseEntity<ControllerResponse<?>> getBookListByAuthorId(@PathVariable Long authorId) {
    Long userId = userService.getCurrentUser().getId();

    List<BookResponseDTO> bookList = authorService.getBookListByAuthorId(authorId, userId);

    return ResponseEntity.ok(ControllerResponse.success(AUTHOR_BOOKS_FETCHED_MESSAGE, bookList));
  }

  @GetMapping("/{authorId}")
  public ResponseEntity<ControllerResponse<?>> getAuthorDetailsByAuthorId(
      @PathVariable Long authorId) {
    AuthorResponseDTO authorResponseDTO = authorService.getAuthorDetailsByAuthorId(authorId);

    return ResponseEntity.ok(
        ControllerResponse.success(AUTHOR_DETAILS_FETCHED_MESSAGE, authorResponseDTO));
  }
}
