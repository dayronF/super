package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.dto.User.UserMessage;
import com.example.demo.dto.User.UserRequest;
import com.example.demo.dto.User.UserResponse;
import com.example.demo.entity.UserEntity;
import com.example.demo.enums.RolEnum;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponse> getAllUser() {

        List<UserEntity> users = userRepository.findAll();
        List<UserResponse> usersR = new ArrayList<>();

        for (UserEntity user : users) {

            UserResponse response = new UserResponse();

            response.setId(user.getId().intValue());
            response.setName(user.getName());
            response.setEmail(user.getEmail());
            response.setCreatedAt(user.getCreated_at());

            if (user.getRolId().equals(1L)) {
                response.setRole("ADMIN");
            } else if (user.getRolId().equals(2L)) {
                response.setRole("CASHIER");
            } else {
                response.setRole("SUPPLIER");
            }

            usersR.add(response);
        }

        return usersR;
    }

    public UserResponse getById(Long id) {

        Optional<UserEntity> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            return null;
        }

        UserEntity user = userOptional.get();

        UserResponse response = new UserResponse();

        response.setId(user.getId().intValue());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setCreatedAt(user.getCreated_at());

        if (user.getRolId().equals(1L)) {
            response.setRole("ADMIN");
        } else if (user.getRolId().equals(2L)) {
            response.setRole("CASHIER");
        } else {
            response.setRole("SUPPLIER");
        }

        return response;
    }

    public UserMessage create(UserRequest request) {

        UserMessage message = new UserMessage();

        if (userRepository.existsByEmail(request.getEmail())) {
            message.setUserMessage("El correo ya existe");
            return message;
        }

        UserEntity user = new UserEntity();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        RolEnum role = RolEnum.valueOf(request.getRole().toUpperCase());
        user.setRolId(role.getId());

        userRepository.save(user);

        message.setUserMessage("Usuario creado exitosamente");

        return message;
    }

    public UserMessage delete(Long id) {

        UserMessage message = new UserMessage();

        Optional<UserEntity> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            message.setUserMessage("Usuario no encontrado");
            return message;
        }

        userRepository.delete(userOptional.get());

        message.setUserMessage("Usuario eliminado correctamente");

        return message;
    }
}