package com.example.demo.dto.User;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserResponse {
     private Integer id;
    private String name;
    private String email;
    private String role;
    private LocalDateTime createdAt;
}
