package com.myreads.MyReads.controllers;

import com.myreads.MyReads.common.ControllerResponse;
import com.myreads.MyReads.dto.AuthorGenreCreateRequest;
import com.myreads.MyReads.services.AuthorGenreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("author_genre")
public class AuthorGenreController {

  public static final String AUTHOR_GENRE_CREATED_MESSAGE = "AuthorGenre created.";
  public static final String AUTHOR_GENRE_FETCHED_MESSAGE = "AuthorGenre fetched.";
  public static final String ALL_AUTHOR_GENRE_FETCHED_MESSAGE = "All AuthorGenres fetched.";

  private final AuthorGenreService authorGenreService;

  public AuthorGenreController(AuthorGenreService authorGenreService) {
    this.authorGenreService = authorGenreService;
  }

  @PostMapping("/create")
  public ResponseEntity<ControllerResponse<?>> createAuthorGenre(
      @RequestBody AuthorGenreCreateRequest authorGenreCreateRequest) {

    authorGenreService.createAuthorGenre(authorGenreCreateRequest);

    return ResponseEntity.ok(ControllerResponse.success(AUTHOR_GENRE_CREATED_MESSAGE));
  }

  @GetMapping("/all")
  public ResponseEntity<ControllerResponse<?>> getAllAuthorGenres() {

    return ResponseEntity.ok(
        ControllerResponse.success(
            ALL_AUTHOR_GENRE_FETCHED_MESSAGE, authorGenreService.getAllAuthorGenres()));
  }

  @GetMapping("/{authorId}")
  public ResponseEntity<ControllerResponse<?>> getAuthorGenresByAuthorId(
      @PathVariable Long authorId) {

    return ResponseEntity.ok(
        ControllerResponse.success(
            AUTHOR_GENRE_FETCHED_MESSAGE, authorGenreService.getAuthorGenreByAuthorId(authorId)));
  }
}
