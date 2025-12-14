package com.myreads.MyReads.controllers;

import com.myreads.MyReads.common.ControllerResponse;
import com.myreads.MyReads.dto.AuthorResponseDTO;
import com.myreads.MyReads.dto.BookResponseDTO;
import com.myreads.MyReads.exceptions.AuthorAlreadyExistsException;
import com.myreads.MyReads.dto.AuthorCreateRequest;
import com.myreads.MyReads.services.AuthorGenreService;
import com.myreads.MyReads.services.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
@CrossOrigin
public class AuthorController {
  public final String AUTHOR_CREATED_MESSAGE = "Author created.";
  private final AuthorService authorService;
  private final AuthorGenreService authorGenreService;

  public AuthorController(AuthorService authorService, AuthorGenreService authorGenreService) {
    this.authorService = authorService;
    this.authorGenreService = authorGenreService;
  }

  @PostMapping("/create")
  public ResponseEntity<ControllerResponse<String>> create(
      @RequestBody AuthorCreateRequest authorCreateRequest) {

    try {
      authorService.create(authorCreateRequest);
    } catch (AuthorAlreadyExistsException exception) {
      return ResponseEntity.badRequest().body(new ControllerResponse<>(exception.getMessage()));
    }

    return ResponseEntity.ok(new ControllerResponse<>(AUTHOR_CREATED_MESSAGE));
  }

  @GetMapping("/all")
  public ResponseEntity<ControllerResponse<?>> gelAll() {

    return ResponseEntity.ok(new ControllerResponse<>(authorService.getAll()));
  }

  @GetMapping("/{authorId}/books")
  public ResponseEntity<ControllerResponse<?>> getBookListByAuthorId(@PathVariable Long authorId) {
    List<BookResponseDTO> bookList = authorService.getBookListByAuthorId(authorId);

    return ResponseEntity.ok(new ControllerResponse<>(bookList));
  }

  @GetMapping("/{authorId}")
  public ResponseEntity<ControllerResponse<?>> getAuthorDetailsByAuthorId(
      @PathVariable Long authorId) {

    AuthorResponseDTO authorResponseDTO = authorService.getAuthorDetailsByAuthorId(authorId);

    return ResponseEntity.ok(new ControllerResponse<>(authorResponseDTO));
  }
}
