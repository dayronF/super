package com.example.demo.dto.login;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
