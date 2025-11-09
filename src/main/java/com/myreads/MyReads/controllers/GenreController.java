package com.myreads.MyReads.controllers;

import com.myreads.MyReads.common.ControllerResponse;
import com.myreads.MyReads.dto.GenreCreateRequest;
import com.myreads.MyReads.exceptions.GenreAlreadyExistsException;
import com.myreads.MyReads.services.GenreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/genres")
@CrossOrigin
public class GenreController {

    private final String GENRE_CREATED_MESSAGE = "Genre created";

    private final GenreService genreService;

    public GenreController(GenreService genreService){
        this.genreService = genreService;
    }

    @PostMapping("/create")
    public ResponseEntity<ControllerResponse<?>> createGenre(@RequestBody GenreCreateRequest genreCreateRequest){

        try{
            genreService.createGenre(genreCreateRequest);
        } catch (GenreAlreadyExistsException exception){
            return ResponseEntity.badRequest().body(new ControllerResponse<>(exception.getMessage()));
        }

        return ResponseEntity.ok(new ControllerResponse<>(GENRE_CREATED_MESSAGE));
    }

    @GetMapping("/all")
    public ResponseEntity<ControllerResponse<?>> getAllGenres(){

        return ResponseEntity.ok(new ControllerResponse<>(genreService.getAllGenres()));
    }
}
