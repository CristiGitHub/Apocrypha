package com.Apocrypha.Apocrypha.service;

import com.Apocrypha.Apocrypha.dtos.RegisterDto;
import com.Apocrypha.Apocrypha.exceptions.UserAlreadyExistException;
import com.Apocrypha.Apocrypha.models.User;
import com.Apocrypha.Apocrypha.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserKeycloakService {

    private final UserRepository UserRepository;
    private final KeycloakAdminService keycloakAdminService;

    @Autowired
    public UserKeycloakService(UserRepository UserRepository, KeycloakAdminService keycloakAdminService) {
        this.UserRepository = UserRepository;
        this.keycloakAdminService = keycloakAdminService;
    }


    public void registerUser(RegisterDto registerDto) throws UserAlreadyExistException {
        if (UserRepository.existsByEmailOrUsername(registerDto.getEmail(), registerDto.getUserName()))
            throw new UserAlreadyExistException(registerDto.getUserName());

        User keycloakUser = User.builder()
                .firstName(registerDto.getFirstName())
                .lastName(registerDto.getLastName())
                .email(registerDto.getEmail())
                .username(registerDto.getUserName())
                .password(registerDto.getPassword())
                .build();
        UserRepository.save(keycloakUser);

        keycloakAdminService.registerUser(registerDto.getUserName(), registerDto.getPassword(), "ROLE_USER");
    }

    public User getUser(String username) {
        User user = UserRepository.findByUsername(username);
        user.setPassword("");
        return user;
    }

}
