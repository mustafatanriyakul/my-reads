package com.myreads.MyReads.controllers;

import com.myreads.MyReads.common.ControllerResponse;
import com.myreads.MyReads.exceptions.InvalidPasswordException;
import com.myreads.MyReads.exceptions.InvalidUsernameException;
import com.myreads.MyReads.exceptions.UsernameAlreadyExistsException;
import com.myreads.MyReads.dto.UserLoginRequest;
import com.myreads.MyReads.dto.UserRegisterRequest;
import com.myreads.MyReads.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    public final String USER_REGISTERED = "User registered successfully";
    public final String USER_LOGGED_IN = "User logged in successfully";
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<ControllerResponse<String>> register(@RequestBody UserRegisterRequest registerRequest){
        try{
            userService.register(registerRequest);
        } catch (UsernameAlreadyExistsException exception){
            return ResponseEntity.badRequest().body(new ControllerResponse<>(exception.getMessage()));
        }

        return ResponseEntity.ok(new ControllerResponse<>(USER_REGISTERED));
    }


    @PostMapping("/login")
    public ResponseEntity<ControllerResponse<String>> login(@RequestBody UserLoginRequest loginRequest){
        try {
            userService.login(loginRequest);
        } catch (InvalidUsernameException |InvalidPasswordException exception){
            return ResponseEntity.badRequest().body(new ControllerResponse<>(exception.getMessage()));
        }

        return ResponseEntity.ok(new ControllerResponse<>(USER_LOGGED_IN));
    }
}

