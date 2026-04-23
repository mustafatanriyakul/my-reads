package com.myreads.MyReads.controllers;

import com.myreads.MyReads.common.ControllerResponse;
import com.myreads.MyReads.dto.GenreCreateRequest;
import com.myreads.MyReads.services.GenreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/genres")
public class GenreController {

  public static final String GENRE_CREATED_MESSAGE = "Genre created.";
  public static final String ALL_GENRES_FETCHED_MESSAGE = "All genres fetched.";

  private final GenreService genreService;

  public GenreController(GenreService genreService) {
    this.genreService = genreService;
  }

  @PostMapping("/create")
  public ResponseEntity<ControllerResponse<?>> createGenre(
      @RequestBody GenreCreateRequest genreCreateRequest) {

    genreService.createGenre(genreCreateRequest);

    return ResponseEntity.ok(ControllerResponse.success(GENRE_CREATED_MESSAGE));
  }

  @GetMapping("/all")
  public ResponseEntity<ControllerResponse<?>> getAllGenres() {

    return ResponseEntity.ok(
        ControllerResponse.success(ALL_GENRES_FETCHED_MESSAGE, genreService.getAllGenres()));
  }
}
