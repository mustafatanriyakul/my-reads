package com.myreads.MyReads.controllers;

import com.myreads.MyReads.common.ControllerResponse;
import com.myreads.MyReads.exceptions.InvalidPasswordException;
import com.myreads.MyReads.exceptions.InvalidUsernameException;
import com.myreads.MyReads.exceptions.UsernameAlreadyExistsException;
import com.myreads.MyReads.dto.UserLoginRequest;
import com.myreads.MyReads.dto.UserRegisterRequest;
import com.myreads.MyReads.services.UserService;
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
    public final String USER_REGISTERED = "User registered successfully";
    public final String USER_LOGGED_IN = "User logged in successfully";
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<ControllerResponse<String>> register(@Valid @RequestBody UserRegisterRequest registerRequest){
        try{
            userService.register(registerRequest);
            return ResponseEntity.ok(new ControllerResponse<>(USER_REGISTERED));
        } catch (UsernameAlreadyExistsException exception){
            return ResponseEntity.badRequest().body(new ControllerResponse<>(exception.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ControllerResponse<String>> login(@RequestBody UserLoginRequest loginRequest){
        try {
            userService.login(loginRequest);
            return ResponseEntity.ok(new ControllerResponse<>(USER_LOGGED_IN));
        } catch (InvalidUsernameException |InvalidPasswordException exception){
            return ResponseEntity.badRequest().body(new ControllerResponse<>(exception.getMessage()));
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}

