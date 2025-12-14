package com.myreads.MyReads.controllers;

import com.myreads.MyReads.common.ControllerResponse;
import com.myreads.MyReads.config.CookieUtils;
import com.myreads.MyReads.exceptions.InvalidPasswordException;
import com.myreads.MyReads.exceptions.InvalidUsernameException;
import com.myreads.MyReads.exceptions.UsernameAlreadyExistsException;
import com.myreads.MyReads.dto.UserLoginRequest;
import com.myreads.MyReads.dto.UserRegisterRequest;
import com.myreads.MyReads.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
  public final String USER_REGISTERED = "User registered successfully.";
  public final String USER_LOGGED_IN = "User logged in successfully.";
  public final String COOKIE_DELETED = "Cookie deleted.";
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/signup")
  public ResponseEntity<ControllerResponse<?>> signup(
      @Valid @RequestBody UserRegisterRequest registerRequest) {
    try {
      userService.signup(registerRequest);
      return ResponseEntity.ok(new ControllerResponse<>(USER_REGISTERED));
    } catch (UsernameAlreadyExistsException exception) {
      return ResponseEntity.badRequest().body(new ControllerResponse<>(exception.getMessage()));
    }
  }

  @PostMapping("/login")
  public ResponseEntity<ControllerResponse<?>> login(
      @RequestBody UserLoginRequest loginRequest, HttpServletResponse response) {
    try {
      String token = userService.login(loginRequest);
      Cookie cookie = CookieUtils.createCookie("token", token);
      response.addCookie(cookie);

      return ResponseEntity.ok(new ControllerResponse<>(USER_LOGGED_IN));
    } catch (InvalidUsernameException | InvalidPasswordException exception) {
      return ResponseEntity.badRequest().body(new ControllerResponse<>(exception.getMessage()));
    }
  }

  @PostMapping("/logout")
  public ResponseEntity<ControllerResponse<?>> logout(HttpServletResponse response) {
    Cookie cookie = CookieUtils.createCookie("token", "");
    cookie.setMaxAge(0);

    response.addCookie(cookie);
    return ResponseEntity.ok(new ControllerResponse<>(COOKIE_DELETED));
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            (error) -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              errors.put(fieldName, errorMessage);
            });
    return errors;
  }
}
