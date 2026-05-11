package com.example.demo.dto.User;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private Long role;
    private LocalDateTime createdAt;

}