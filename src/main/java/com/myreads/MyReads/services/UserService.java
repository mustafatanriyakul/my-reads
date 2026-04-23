package com.myreads.MyReads.services;

import com.myreads.MyReads.config.CookieUtils;
import com.myreads.MyReads.dto.UserLoginRequest;
import com.myreads.MyReads.dto.UserRegisterRequest;
import com.myreads.MyReads.exceptions.InvalidPasswordException;
import com.myreads.MyReads.exceptions.InvalidUsernameException;
import com.myreads.MyReads.exceptions.RefreshTokenExpiredException;
import com.myreads.MyReads.exceptions.UsernameAlreadyExistsException;
import com.myreads.MyReads.models.User;
import com.myreads.MyReads.models.UserPrincipal;
import com.myreads.MyReads.repositories.UserRepository;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final JWTService jwtService;
  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  public static final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 15; //  15 minutes

  public UserService(
      JWTService jwtService,
      AuthenticationManager authenticationManager,
      UserRepository userRepository) {
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
  }

  private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  public void signup(UserRegisterRequest registerRequest) {

    if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
      throw new UsernameAlreadyExistsException(registerRequest.getUsername());
    }

    String encodedPassword = encoder.encode(registerRequest.getPassword());

    User newUser = new User(registerRequest.getUsername(), encodedPassword);
    newUser.setRole(User.Role.USER);

    userRepository.save(newUser);
  }

  public Long login(UserLoginRequest loginRequest) {
    Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());

    if (user.isEmpty()) {
      throw new InvalidUsernameException(loginRequest.getUsername());
    }

    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              loginRequest.getUsername(), loginRequest.getPassword()));

      return user.get().getId();
    } catch (BadCredentialsException exception) {
      throw new InvalidPasswordException();
    }
  }

  public String generateNewAccessToken(HttpServletRequest request) {
    String refreshToken = CookieUtils.extractTokenFromCookies(request, "refresh_token");

    if (refreshToken == null) {
      throw new RefreshTokenExpiredException();
    }

    String username = jwtService.extractUserName(refreshToken);
    Long userId = jwtService.extractUserId(refreshToken);

    String newAccessToken = jwtService.generateToken(userId, username, ACCESS_TOKEN_EXPIRATION);

    return newAccessToken;
  }

  public User getCurrentUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();

    return userPrincipal.getUser();
  }
}
