package com.myreads.MyReads.controllers;

import com.myreads.MyReads.exceptions.AuthorNotFoundException;
import com.myreads.MyReads.exceptions.BookAlreadyExistsException;
import com.myreads.MyReads.requests.BookCreateRequest;
import com.myreads.MyReads.services.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createBook(@RequestBody BookCreateRequest bookCreateRequest) {

        try {
            bookService.createBook(bookCreateRequest);
        } catch (AuthorNotFoundException | BookAlreadyExistsException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }

        return ResponseEntity.ok("Book created");
    }
}
