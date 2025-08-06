package com.myreads.MyReads.requests;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String username;
    private String password;
}
