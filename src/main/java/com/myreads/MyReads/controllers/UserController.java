package com.myreads.MyReads.controllers;

import com.myreads.MyReads.common.ControllerResponse;
import com.myreads.MyReads.config.CookieUtils;
import com.myreads.MyReads.dto.UserLoginRequest;
import com.myreads.MyReads.dto.UserRegisterRequest;
import com.myreads.MyReads.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
  public final String USER_SIGNED_UP_MESSAGE = "User signed up.";
  public final String USER_LOGGED_IN_MESSAGE = "User logged in.";
  public final String COOKIE_DELETED_MESSAGE = "Cookie deleted.";
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/signup")
  public ResponseEntity<ControllerResponse<?>> signup(
      @Valid @RequestBody UserRegisterRequest registerRequest) {
    userService.signup(registerRequest);
    return ResponseEntity.ok(ControllerResponse.success(USER_SIGNED_UP_MESSAGE));
  }

  @PostMapping("/login")
  public ResponseEntity<ControllerResponse<?>> login(
      @RequestBody UserLoginRequest loginRequest, HttpServletResponse response) {
    String token = userService.login(loginRequest);
    Cookie cookie = CookieUtils.createCookie("token", token);
    response.addCookie(cookie);

    return ResponseEntity.ok(ControllerResponse.success(USER_LOGGED_IN_MESSAGE));
  }

  @PostMapping("/logout")
  public ResponseEntity<ControllerResponse<?>> logout(HttpServletResponse response) {
    Cookie cookie = CookieUtils.createCookie("token", "");
    cookie.setMaxAge(0);

    response.addCookie(cookie);
    return ResponseEntity.ok(ControllerResponse.success(COOKIE_DELETED_MESSAGE));
  }
}
