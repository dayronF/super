package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.dto.User.UserMessage;
import com.example.demo.dto.User.UserRequest;
import com.example.demo.dto.User.UserResponse;
import com.example.demo.entity.UserEntity;
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

            response.setId(user.getId());
            response.setName(user.getName());
            response.setEmail(user.getEmail());
            response.setRole(user.getRolId());
            response.setCreatedAt(user.getCreated_at());

            usersR.add(response);

        }

        return usersR;

    }

    public UserResponse getById(Long id) {

        Optional<UserEntity> userO = userRepository.findById(id);

        if (userO.isEmpty()) {
            return null;
        }

        UserEntity entity = userO.get();

        UserResponse response = new UserResponse();

        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setEmail(entity.getEmail());
        response.setRole(entity.getRolId());
        response.setCreatedAt(entity.getCreated_at());

        return response;

    }

    public UserMessage create(UserRequest request) {

        UserMessage message = new UserMessage();

        if (userRepository.existsByEmail(request.getEmail())) {

            message.setUserMessage("Ya existe un usuario con ese correo");
            return message;

        }

        UserEntity user = new UserEntity();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        userRepository.save(user);

        message.setUserMessage("Usuario creado exitosamente");

        return message;

    }

    public UserMessage update(Long id, UserRequest request) {

        Optional<UserEntity> userO = userRepository.findById(id);

        UserMessage message = new UserMessage();

        if (userO.isEmpty()) {

            message.setUserMessage("Usuario no encontrado");
            return message;

        }

        UserEntity entity = userO.get();

        entity.setName(request.getName());
        entity.setEmail(request.getEmail());
        entity.setPassword(request.getPassword());

        userRepository.save(entity);

        message.setUserMessage("Usuario actualizado correctamente");

        return message;

    }

    public UserMessage delete(Long id) {

        Optional<UserEntity> userO = userRepository.findById(id);

        UserMessage message = new UserMessage();

        if (userO.isEmpty()) {

            message.setUserMessage("Usuario no encontrado");
            return message;

        }

        UserEntity entity = userO.get();

        userRepository.delete(entity);

        message.setUserMessage("Usuario eliminado correctamente");

        return message;

    }

}