package com.myreads.MyReads.controllers;

import com.myreads.MyReads.common.ControllerResponse;
import com.myreads.MyReads.exceptions.BookNotFoundException;
import com.myreads.MyReads.exceptions.UserNotFoundException;
import com.myreads.MyReads.models.MyBook;
import com.myreads.MyReads.requests.MyBookCreateRequest;
import com.myreads.MyReads.services.MyBookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mybooks")
public class MyBookController {
    private final MyBookService myBookService;

    public MyBookController(MyBookService myBookService) {
        this.myBookService = myBookService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addBookToMybooks(@RequestBody MyBookCreateRequest myBookCreateRequest) {

        try {
            myBookService.addBookToMybooks(myBookCreateRequest);
        } catch (UserNotFoundException | BookNotFoundException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }

        return ResponseEntity.ok("Book added.");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ControllerResponse> getUsersBook(@PathVariable Long userId) {

        try {
            List<MyBook> myBooks = myBookService.getMybooksOfUser(userId);

            return ResponseEntity.ok(new ControllerResponse("Books fetched successfully",myBooks));
        }catch (UserNotFoundException exception){
            return ResponseEntity.badRequest().body(new ControllerResponse<>(exception.getMessage()));
        }

    }
}
