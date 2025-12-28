package com.myreads.MyReads.dto;

import lombok.Data;

@Data
public class UserLoginRequest {
  private String username;
  private String password;
}
