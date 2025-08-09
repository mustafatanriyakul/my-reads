package com.myreads.MyReads.controllers;

import com.myreads.MyReads.exceptions.InvalidPasswordException;
import com.myreads.MyReads.exceptions.InvalidUsernameException;
import com.myreads.MyReads.exceptions.UsernameAlreadyExistsException;
import com.myreads.MyReads.requests.UserLoginRequest;
import com.myreads.MyReads.requests.UserRegisterRequest;
import com.myreads.MyReads.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterRequest registerRequest){
        try{
            userService.register(registerRequest);
        } catch (UsernameAlreadyExistsException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }

        return ResponseEntity.ok("User registered successfully");
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest loginRequest){
        try {
            userService.login(loginRequest);
        } catch (InvalidUsernameException |InvalidPasswordException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }

        return ResponseEntity.ok("Login successfully");
    }
}

