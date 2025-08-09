package com.myreads.MyReads.services;

import com.myreads.MyReads.exceptions.InvalidPasswordException;
import com.myreads.MyReads.exceptions.InvalidUsernameException;
import com.myreads.MyReads.exceptions.UsernameAlreadyExistsException;
import com.myreads.MyReads.models.User;
import com.myreads.MyReads.repositories.UserRepository;
import com.myreads.MyReads.requests.UserLoginRequest;
import com.myreads.MyReads.requests.UserRegisterRequest;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void register(UserRegisterRequest registerRequest) {

        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException(registerRequest.getUsername());
        }

        User newUser = new User(registerRequest.getUsername(), registerRequest.getPassword());

        userRepository.save(newUser);
    }


    public void login(UserLoginRequest loginRequest) {

        if (userRepository.findByUsername(loginRequest.getUsername()).isEmpty()) {
            throw new InvalidUsernameException(loginRequest.getUsername());
        }

        if (!(userRepository.findByUsername(loginRequest.getUsername()).get().getPassword().equals(loginRequest.getPassword()))) {
            throw new InvalidPasswordException();
        }
    }
}
