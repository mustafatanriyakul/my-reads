package com.myreads.MyReads.controllers;

import com.myreads.MyReads.common.ControllerResponse;
import com.myreads.MyReads.dto.UserBookResponseDTO;
import com.myreads.MyReads.dto.BookStatusRequest;
import com.myreads.MyReads.services.UserBookService;
import com.myreads.MyReads.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mybooks")
public class UserBookController {
  public static final String BOOK_ADDED_MESSAGE = "Book added.";
  public static final String BOOK_STATUS_CHANGED_MESSAGE = "Book status changed.";
  public static final String BOOKS_FETCHED_MESSAGE = "Book fetched.";
  private final UserBookService userBookService;
  private final UserService userService;

  public UserBookController(UserBookService userBookService, UserService userService) {
    this.userBookService = userBookService;
    this.userService = userService;
  }

  @PostMapping("/add")
  public ResponseEntity<ControllerResponse<String>> addBookUserBooks(
      @RequestBody BookStatusRequest bookStatusRequest) {

    Long userId = userService.getCurrentUser().getId();

    userBookService.addBookToUserBooks(bookStatusRequest, userId);
    return ResponseEntity.ok(ControllerResponse.success(BOOK_ADDED_MESSAGE));
  }

  @GetMapping()
  public ResponseEntity<ControllerResponse<List<UserBookResponseDTO>>> getUserBooks() {

    Long userId = userService.getCurrentUser().getId();

    List<UserBookResponseDTO> userBookResponseDTOS = userBookService.getUserBookByUserId(userId);

    return ResponseEntity.ok(
        ControllerResponse.success(BOOKS_FETCHED_MESSAGE, userBookResponseDTOS));
  }

  @PostMapping("/update")
  public ResponseEntity<ControllerResponse<?>> updateUserBookStatus(
      @RequestBody BookStatusRequest bookStatusRequest) {

    Long userId = userService.getCurrentUser().getId();

    userBookService.updateUserBookStatus(bookStatusRequest, userId);
    return ResponseEntity.ok(ControllerResponse.success(BOOK_STATUS_CHANGED_MESSAGE));
  }
}
