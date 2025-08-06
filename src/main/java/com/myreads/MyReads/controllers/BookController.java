package com.myreads.MyReads.controllers;

import com.myreads.MyReads.models.Book;
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

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @PostMapping("/create")
    public ResponseEntity<Book> createBook(@RequestBody BookCreateRequest bookCreateRequest){
        Book book = bookService.createBook(bookCreateRequest);
        return ResponseEntity.ok(book);
    }
}
