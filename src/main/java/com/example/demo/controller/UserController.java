package com.example.demo.controller;


import com.example.demo.dto.User.UserMessage;
import com.example.demo.dto.User.UserRequest;
import com.example.demo.dto.User.UserResponse;
import com.example.demo.enums.RolEnum;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/list")
    public ResponseEntity<List<UserResponse>> listUsers(HttpServletRequest httpServletRequest) {
        try {
            Long rolId = (Long) httpServletRequest.getAttribute("rolId");

            if (!rolId.equals(RolEnum.ADMIN.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }

            List<UserResponse> userResponseList = userService.getAllUser();
            return ResponseEntity.status(HttpStatus.OK).body(userResponseList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/find/{userId}")
    public ResponseEntity<UserResponse> findUserById(
            @PathVariable Long userId,
            HttpServletRequest httpServletRequest) {
        try {
            Long rolId = (Long) httpServletRequest.getAttribute("rolId");

            if (!rolId.equals(RolEnum.ADMIN.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }

            UserResponse userResponse = userService.getById(userId);
            if (userResponse == null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            return ResponseEntity.status(HttpStatus.OK).body(userResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<UserMessage> createUser(
            @RequestBody UserRequest userRequest,
            HttpServletRequest httpServletRequest) {
        try {
            Long rolId = (Long) httpServletRequest.getAttribute("rolId");

            if (!rolId.equals(RolEnum.ADMIN.getId())) {
                UserMessage userMessage = new UserMessage();
                userMessage.setUserMessage("Unauthorized: admin role required");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(userMessage);
            }

            UserMessage userMessage = userService.create(userRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(userMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserMessage> updateUser(
            @PathVariable Long userId,
            @RequestBody UserRequest userRequest,
            HttpServletRequest httpServletRequest) {
        try {
            Long rolId = (Long) httpServletRequest.getAttribute("rolId");

            if (!rolId.equals(RolEnum.ADMIN.getId())) {
                UserMessage userMessage = new UserMessage();
                userMessage.setUserMessage("Unauthorized: admin role required");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(userMessage);
            }

            UserMessage userMessage = userService.update(userId, userRequest);
            return ResponseEntity.status(HttpStatus.OK).body(userMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<UserMessage> deleteUser(
            @PathVariable Long userId,
            HttpServletRequest httpServletRequest) {
        try {
            Long rolId = (Long) httpServletRequest.getAttribute("rolId");

            if (!rolId.equals(RolEnum.ADMIN.getId())) {
                UserMessage userMessage = new UserMessage();
                userMessage.setUserMessage("Unauthorized: admin role required");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(userMessage);
            }

            UserMessage userMessage = userService.delete(userId);
            return ResponseEntity.status(HttpStatus.OK).body(userMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}