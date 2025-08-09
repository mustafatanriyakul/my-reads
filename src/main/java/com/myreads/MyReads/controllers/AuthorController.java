package com.myreads.MyReads.controllers;

import com.myreads.MyReads.exceptions.AuthorAlreadyExistsException;
import com.myreads.MyReads.requests.AuthorCreateRequest;
import com.myreads.MyReads.services.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAuthor(@RequestBody AuthorCreateRequest authorCreateRequest) {

        try {
            authorService.createAuthor(authorCreateRequest);
        } catch (AuthorAlreadyExistsException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }

        return ResponseEntity.ok("Author created.");

    }
}
