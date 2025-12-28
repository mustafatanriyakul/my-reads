package com.myreads.MyReads.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class CookieUtils {

  public static Cookie createCookie(String cookieName, String cookieValue) {

    Cookie cookie = new Cookie(cookieName, cookieValue);
    cookie.setHttpOnly(true);
    cookie.setSecure(true);
    cookie.setPath("/");
    cookie.setMaxAge(60 * 30); // 30 minutes

    return cookie;
  }

  public static String extractTokenFromCookies(HttpServletRequest request) {
    if (request.getCookies() == null) return null;

    for (Cookie c : request.getCookies()) {
      if ("token".equals(c.getName())) {
        return c.getValue();
      }
    }
    return null;
  }
}
