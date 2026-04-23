package com.myreads.MyReads.controllers;

import com.myreads.MyReads.common.ControllerResponse;
import com.myreads.MyReads.config.CookieUtils;
import com.myreads.MyReads.dto.UserLoginRequest;
import com.myreads.MyReads.dto.UserRegisterRequest;
import com.myreads.MyReads.models.User;
import com.myreads.MyReads.services.JWTService;
import com.myreads.MyReads.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Ref;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
  public final String USER_SIGNED_UP_MESSAGE = "User signed up.";
  public final String USER_LOGGED_IN_MESSAGE = "User logged in.";
  public final String ACCESS_TOKEN_COOKIE_DELETED_MESSAGE = "Access token cookie deleted.";
  public final String ACCESS_TOKEN_RENEWED_MESSAGE = "Access token renewed.";
  public static final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 15; // 15 minutes
  public static final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 24; // 1 day
  private final UserService userService;
  private final JWTService jwtService;

  public UserController(UserService userService, JWTService jwtService) {
    this.userService = userService;
    this.jwtService = jwtService;
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
    Long userId = userService.login(loginRequest);
    String accessToken =
        jwtService.generateToken(userId, loginRequest.getUsername(), ACCESS_TOKEN_EXPIRATION);
    String refreshToken =
        jwtService.generateToken(userId, loginRequest.getUsername(), REFRESH_TOKEN_EXPIRATION);

    Cookie accessTokenCookie = CookieUtils.createAccessTokenCookie(accessToken);
    Cookie refreshTokenCookie = CookieUtils.createRefreshTokenCookie(refreshToken);
    response.addCookie(accessTokenCookie);
    response.addCookie(refreshTokenCookie);

    return ResponseEntity.ok(ControllerResponse.success(USER_LOGGED_IN_MESSAGE));
  }

  @PostMapping("/logout")
  public ResponseEntity<ControllerResponse<?>> logout(HttpServletResponse response) {
    Cookie accessTokenCookie = CookieUtils.createAccessTokenCookie("");
    accessTokenCookie.setMaxAge(0);

    Cookie refreshTokenCookie = CookieUtils.createRefreshTokenCookie("");
    refreshTokenCookie.setMaxAge(0);

    response.addCookie(accessTokenCookie);
    response.addCookie(refreshTokenCookie);
    return ResponseEntity.ok(ControllerResponse.success(ACCESS_TOKEN_COOKIE_DELETED_MESSAGE));
  }

  @PostMapping("/refresh")
  public ResponseEntity<ControllerResponse<?>> refresh(
      HttpServletRequest request, HttpServletResponse response) {

    String newAccessToken = userService.generateNewAccessToken(request);
    Cookie accessTokenCookie = CookieUtils.createAccessTokenCookie(newAccessToken);
    response.addCookie(accessTokenCookie);

    return ResponseEntity.ok(ControllerResponse.success(ACCESS_TOKEN_RENEWED_MESSAGE));
  }
}
