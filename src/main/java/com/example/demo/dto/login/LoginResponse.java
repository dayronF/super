package com.example.demo.dto.login;

import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    private Long rolId;
}
