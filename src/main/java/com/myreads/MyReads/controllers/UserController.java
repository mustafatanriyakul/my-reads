package com.myreads.MyReads.controllers;

import com.myreads.MyReads.common.ControllerResponse;
import com.myreads.MyReads.exceptions.InvalidPasswordException;
import com.myreads.MyReads.exceptions.InvalidUsernameException;
import com.myreads.MyReads.exceptions.UsernameAlreadyExistsException;
import com.myreads.MyReads.dto.UserLoginRequest;
import com.myreads.MyReads.dto.UserRegisterRequest;
import com.myreads.MyReads.models.User;
import com.myreads.MyReads.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
    public final String USER_REGISTERED = "User registered successfully.";
    public final String USER_LOGGED_IN = "User logged in successfully.";
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/signup")
    public ResponseEntity<ControllerResponse<?>> signup(@RequestBody UserRegisterRequest registerRequest){
        try{
            User user = userService.signup(registerRequest);
            return ResponseEntity.ok(new ControllerResponse<>(USER_REGISTERED, user));
        } catch (UsernameAlreadyExistsException exception){
            return ResponseEntity.badRequest().body(new ControllerResponse<>(exception.getMessage()));
        }


    }


    @PostMapping("/login")
    public ResponseEntity<ControllerResponse<?>> login(@RequestBody UserLoginRequest loginRequest){
        try {
            Optional<User> user = userService.login(loginRequest);
            return ResponseEntity.ok(new ControllerResponse<>(USER_LOGGED_IN, user));
        } catch (InvalidUsernameException |InvalidPasswordException exception){
            return ResponseEntity.badRequest().body(new ControllerResponse<>(exception.getMessage()));
        }


    }
}

