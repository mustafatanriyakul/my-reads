package com.myreads.MyReads.controllers;

import com.myreads.MyReads.common.ControllerResponse;
import com.myreads.MyReads.config.CookieUtils;
import com.myreads.MyReads.dto.UserBookResponseDTO;
import com.myreads.MyReads.exceptions.BookNotFoundException;
import com.myreads.MyReads.exceptions.UserAlreadyHasThisBookException;
import com.myreads.MyReads.exceptions.UserNotFoundException;
import com.myreads.MyReads.dto.UserBookCreateRequest;
import com.myreads.MyReads.services.JWTService;
import com.myreads.MyReads.services.UserBookService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mybooks")
@CrossOrigin
public class UserBookController {
  public static String BOOK_ADDED = "Book added.";
  public static String BOOKS_FETCHED = "Book fetched successfully.";
  private final UserBookService userBookService;

  private final JWTService jwtService;

  public UserBookController(UserBookService userBookService, JWTService jwtService) {
    this.userBookService = userBookService;
    this.jwtService = jwtService;
  }

  @PostMapping("/add")
  public ResponseEntity<ControllerResponse<String>> addBookUserBooks(
      @RequestBody UserBookCreateRequest userBookCreateRequest) {

    try {
      userBookService.addBookToUserBooks(userBookCreateRequest);
      return ResponseEntity.ok(new ControllerResponse<>(BOOK_ADDED));
    } catch (UserNotFoundException
        | BookNotFoundException
        | UserAlreadyHasThisBookException exception) {
      return ResponseEntity.badRequest().body(new ControllerResponse<>(exception.getMessage()));
    }
  }

  @GetMapping()
  public ResponseEntity<ControllerResponse<List<UserBookResponseDTO>>> getUserBooks(
      HttpServletRequest request) {

    try {
      String token = CookieUtils.extractTokenFromCookies(request);
      Long userId = jwtService.extractUserId(token);

      List<UserBookResponseDTO> userBookResponseDTOS = userBookService.getUserBookByUserId(userId);

      return ResponseEntity.ok(new ControllerResponse<>(BOOKS_FETCHED, userBookResponseDTOS));
    } catch (UserNotFoundException exception) {
      return ResponseEntity.badRequest().body(new ControllerResponse<>(exception.getMessage()));
    }
  }
}
