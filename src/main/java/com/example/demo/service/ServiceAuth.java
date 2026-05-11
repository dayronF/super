package com.example.demo.service;

import com.example.demo.dto.Auth.LoginRequest;
import com.example.demo.dto.Auth.LoginResponse;
import com.example.demo.dto.User.UserRequest;
import com.example.demo.dto.User.UserResponse;
import com.example.demo.entity.UserEntity;
import com.example.demo.enums.RolEnum;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceAuth {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail()))
            return null;

        // Convertir el rol String que llega en el request a su id del enum
        Long rolId = RolEnum.valueOf(userRequest.getRole().toUpperCase()).getId();

        UserEntity newUserEntity = new UserEntity();
        newUserEntity.setName(userRequest.getName());
        newUserEntity.setEmail(userRequest.getEmail());
        newUserEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        newUserEntity.setRolId(rolId);
        newUserEntity.setCreated_at(LocalDateTime.now());
        userRepository.save(newUserEntity);

        UserResponse userResponse = new UserResponse();
        userResponse.setId(newUserEntity.getId());
        userResponse.setName(newUserEntity.getName());
        userResponse.setEmail(newUserEntity.getEmail());
        userResponse.setCreatedAt(newUserEntity.getCreated_at());
        return userResponse;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isEmpty())
            return null;

        UserEntity userEntity = userOptional.get();

        if (!passwordEncoder.matches(loginRequest.getPassword(), userEntity.getPassword()))
            return null;

        String token = jwtService.generateToken(
                userEntity.getId(),
                userEntity.getRolId(),
                userEntity.getEmail()
        );

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(token);
        return loginResponse;
    }
}