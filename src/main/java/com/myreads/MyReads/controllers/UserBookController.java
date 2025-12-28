package com.myreads.MyReads.controllers;

import com.myreads.MyReads.common.ControllerResponse;
import com.myreads.MyReads.config.CookieUtils;
import com.myreads.MyReads.dto.UserBookResponseDTO;
import com.myreads.MyReads.dto.UserBookCreateRequest;
import com.myreads.MyReads.services.JWTService;
import com.myreads.MyReads.services.UserBookService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mybooks")
public class UserBookController {
  public static final String BOOK_ADDED_MESSAGE = "Book added.";
  public static final String BOOKS_FETCHED_MESSAGE = "Book fetched.";
  private final UserBookService userBookService;

  private final JWTService jwtService;

  public UserBookController(UserBookService userBookService, JWTService jwtService) {
    this.userBookService = userBookService;
    this.jwtService = jwtService;
  }

  @PostMapping("/add")
  public ResponseEntity<ControllerResponse<String>> addBookUserBooks(
      @RequestBody UserBookCreateRequest userBookCreateRequest, HttpServletRequest request) {

    String token = CookieUtils.extractTokenFromCookies(request);
    Long userId = jwtService.extractUserId(token);

    userBookService.addBookToUserBooks(userBookCreateRequest, userId);
    return ResponseEntity.ok(ControllerResponse.success(BOOK_ADDED_MESSAGE));
  }

  @GetMapping()
  public ResponseEntity<ControllerResponse<List<UserBookResponseDTO>>> getUserBooks(
      HttpServletRequest request) {

    String token = CookieUtils.extractTokenFromCookies(request);
    Long userId = jwtService.extractUserId(token);

    List<UserBookResponseDTO> userBookResponseDTOS = userBookService.getUserBookByUserId(userId);

    return ResponseEntity.ok(
        ControllerResponse.success(BOOKS_FETCHED_MESSAGE, userBookResponseDTOS));
  }
}
