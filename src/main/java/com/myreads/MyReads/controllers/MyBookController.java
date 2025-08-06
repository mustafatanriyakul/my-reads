package com.myreads.MyReads.controllers;

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

    public MyBookController(MyBookService myBookService){
        this.myBookService = myBookService;
    }

    @PostMapping("/add")
    public ResponseEntity<MyBook> addBookToMybooks(@RequestBody MyBookCreateRequest myBookCreateRequest){
        MyBook myBook = myBookService.addBookToMybooks(myBookCreateRequest);

        return ResponseEntity.ok(myBook);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<MyBook>> getUsersBook(@PathVariable Long userId){
        List<MyBook> myBooks = myBookService.getMybooksOfUser(userId);

        return ResponseEntity.ok(myBooks);
    }
}
