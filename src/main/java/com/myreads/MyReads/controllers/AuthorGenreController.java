package com.myreads.MyReads.controllers;

import com.myreads.MyReads.common.ControllerResponse;
import com.myreads.MyReads.dto.AuthorGenreCreateRequest;
import com.myreads.MyReads.exceptions.AuthorGenreAlreadyExistsException;
import com.myreads.MyReads.exceptions.AuthorNotFoundException;
import com.myreads.MyReads.exceptions.GenreNotFoundException;
import com.myreads.MyReads.services.AuthorGenreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("author_genre")
@CrossOrigin
public class AuthorGenreController {

  private final String AUTHOR_GENRE_CREATED_MESSAGE = "AuthorGenre created.";

  private final AuthorGenreService authorGenreService;

  public AuthorGenreController(AuthorGenreService authorGenreService) {
    this.authorGenreService = authorGenreService;
  }

  @PostMapping("/create")
  public ResponseEntity<ControllerResponse<?>> createAuthorGenre(
      @RequestBody AuthorGenreCreateRequest authorGenreCreateRequest) {

    try {
      authorGenreService.createAuthorGenre(authorGenreCreateRequest);
    } catch (AuthorGenreAlreadyExistsException
        | AuthorNotFoundException
        | GenreNotFoundException exception) {
      return ResponseEntity.badRequest().body(new ControllerResponse<>(exception.getMessage()));
    }

    return ResponseEntity.ok(new ControllerResponse<>(AUTHOR_GENRE_CREATED_MESSAGE));
  }

  @GetMapping("/all")
  public ResponseEntity<ControllerResponse<?>> getAllAuthorGenres() {

    return ResponseEntity.ok(new ControllerResponse<>(authorGenreService.getAllAuthorGenres()));
  }

  @GetMapping("/{authorId}")
  public ResponseEntity<ControllerResponse<?>> getAuthorGenresByAuthorId(
      @PathVariable Long authorId) {

    return ResponseEntity.ok(
        new ControllerResponse<>(authorGenreService.getAuthorGenreByAuthorId(authorId)));
  }
}
