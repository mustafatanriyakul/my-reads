package com.myreads.MyReads.services;

import com.myreads.MyReads.exceptions.InvalidPasswordException;
import com.myreads.MyReads.exceptions.InvalidUsernameException;
import com.myreads.MyReads.exceptions.UsernameAlreadyExistsException;
import com.myreads.MyReads.models.User;
import com.myreads.MyReads.repositories.UserRepository;
import com.myreads.MyReads.dto.UserLoginRequest;
import com.myreads.MyReads.dto.UserRegisterRequest;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User signup(UserRegisterRequest registerRequest) {

        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException(registerRequest.getUsername());
        }

        String encodedPassword = encoder.encode(registerRequest.getPassword());

        User newUser = new User(registerRequest.getUsername(),
                encodedPassword);

        userRepository.save(newUser);

        return newUser;
    }


    public Optional<User> login(UserLoginRequest loginRequest) {
        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());

        if (user.isEmpty()) {
            throw new InvalidUsernameException(loginRequest.getUsername());
        }

        if (! encoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
            throw new InvalidPasswordException();
        }

        return user;
    }
}
