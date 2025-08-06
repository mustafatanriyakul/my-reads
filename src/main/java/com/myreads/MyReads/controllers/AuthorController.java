package com.myreads.MyReads.controllers;

import com.myreads.MyReads.models.Author;
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

    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    @PostMapping("/create")
    public ResponseEntity<Author> createAuthor(@RequestBody AuthorCreateRequest authorCreateRequest){
        Author createdAuthor = authorService.createAuthor(authorCreateRequest);

        return ResponseEntity.ok(createdAuthor);

    }
}
