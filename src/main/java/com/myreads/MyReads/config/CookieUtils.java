package com.myreads.MyReads.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class CookieUtils {

  public static Cookie createAccessTokenCookie(String token) {

    Cookie cookie = new Cookie("access_token", token);
    cookie.setHttpOnly(true);
    cookie.setSecure(true);
    cookie.setPath("/");
    cookie.setMaxAge(60 * 15); // 15 min

    return cookie;
  }

  public static Cookie createRefreshTokenCookie(String token) {

    Cookie cookie = new Cookie("refresh_token", token);
    cookie.setHttpOnly(true);
    cookie.setSecure(true);
    cookie.setPath("/users/refresh");
    cookie.setMaxAge(60 * 60 * 24); // 1 day

    return cookie;
  }

  public static String extractTokenFromCookies(HttpServletRequest request, String tokenType) {
    if (request.getCookies() == null) return null;

    for (Cookie c : request.getCookies()) {
      if (tokenType.equals(c.getName())) {
        return c.getValue();
      }
    }
    return null;
  }
}
