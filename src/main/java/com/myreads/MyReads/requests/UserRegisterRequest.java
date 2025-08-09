package com.myreads.MyReads.requests;

import lombok.Data;

@Data
public class UserRegisterRequest {
    private String username;
    private String password;
}
