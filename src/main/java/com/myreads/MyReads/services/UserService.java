package com.myreads.MyReads.services;

import com.myreads.MyReads.dto.UserLoginRequest;
import com.myreads.MyReads.dto.UserRegisterRequest;
import com.myreads.MyReads.exceptions.InvalidPasswordException;
import com.myreads.MyReads.exceptions.InvalidUsernameException;
import com.myreads.MyReads.exceptions.UsernameAlreadyExistsException;
import com.myreads.MyReads.models.User;
import com.myreads.MyReads.repositories.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired private JWTService jwtService;

  @Autowired AuthenticationManager authenticationManager;

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  public void signup(UserRegisterRequest registerRequest) {

    if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
      throw new UsernameAlreadyExistsException(registerRequest.getUsername());
    }

    String encodedPassword = encoder.encode(registerRequest.getPassword());

    User newUser = new User(registerRequest.getUsername(), encodedPassword);

    userRepository.save(newUser);
  }

  public String login(UserLoginRequest loginRequest) {
    Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());

    if (user.isEmpty()) {
      throw new InvalidUsernameException(loginRequest.getUsername());
    }

    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              loginRequest.getUsername(), loginRequest.getPassword()));

      return jwtService.generateToken(user.get().getId(), loginRequest.getUsername());
    } catch (BadCredentialsException exception) {
      throw new InvalidPasswordException();
    }
  }
}
