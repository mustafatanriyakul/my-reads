package com.myreads.MyReads.services;

import com.myreads.MyReads.exceptions.UsernameAlreadyExistsException;
import com.myreads.MyReads.models.User;
import com.myreads.MyReads.repositories.UserRepository;
import com.myreads.MyReads.requests.UserLoginRequest;
import com.myreads.MyReads.requests.UserRegisterRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository)   {
        this.userRepository = userRepository;
    }


    public void register(UserRegisterRequest registerRequest){

        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()){
            throw new UsernameAlreadyExistsException(registerRequest.getUsername());
        }

        User newUser = new User(registerRequest.getUsername(), registerRequest.getPassword());

        userRepository.save(newUser);

    }


    public String login(UserLoginRequest loginRequest){
        Optional<User> optionalUser = userRepository.findByUsername(loginRequest.getUsername());

        if (optionalUser.isPresent()){
            if (optionalUser.get().getPassword().equals(loginRequest.getPassword())){
                return "Login successfully";
            }else {
                return "Invalid password";
            }
        }else {
            return "Invalid name";
        }

    }
}
