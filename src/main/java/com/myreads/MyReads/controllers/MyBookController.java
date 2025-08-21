package com.myreads.MyReads.controllers;

import com.myreads.MyReads.common.ControllerResponse;
import com.myreads.MyReads.dto.MyBookResponseDTO;
import com.myreads.MyReads.exceptions.BookNotFoundException;
import com.myreads.MyReads.exceptions.UserAlreadyHasThisBookException;
import com.myreads.MyReads.exceptions.UserNotFoundException;
import com.myreads.MyReads.dto.MyBookCreateRequest;
import com.myreads.MyReads.services.MyBookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mybooks")
@CrossOrigin
public class MyBookController {
    public static String BOOK_ADDED = "Book added.";
    public static String BOOKS_FETCHED = "Book fetched successfully.";
    private final MyBookService myBookService;

    public MyBookController(MyBookService myBookService) {
        this.myBookService = myBookService;
    }

    @PostMapping("/add")
    public ResponseEntity<ControllerResponse<String>> addBookToMybooks(@RequestBody MyBookCreateRequest myBookCreateRequest) {

        try {
            myBookService.addBookToMyBooks(myBookCreateRequest);
        } catch (UserNotFoundException | BookNotFoundException | UserAlreadyHasThisBookException exception) {
            return ResponseEntity.badRequest().body(new ControllerResponse<>(exception.getMessage()));
        }

        return ResponseEntity.ok(new ControllerResponse<>(BOOK_ADDED));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ControllerResponse<List<MyBookResponseDTO>>> getUsersBook(@PathVariable Long userId) {

        try {
            List<MyBookResponseDTO> myBookResponseDTOS = myBookService.getMyBooksByUserId(userId);

            return ResponseEntity.ok(new ControllerResponse<>(BOOKS_FETCHED, myBookResponseDTOS));
        } catch (UserNotFoundException exception) {
            return ResponseEntity.badRequest().body(new ControllerResponse<>(exception.getMessage()));
        }

    }
}
