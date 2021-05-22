package com.Apocrypha.Apocrypha.controller;

import com.Apocrypha.Apocrypha.dtos.LoginDto;
import com.Apocrypha.Apocrypha.dtos.RegisterDto;
import com.Apocrypha.Apocrypha.exceptions.UserAlreadyExistException;
import com.Apocrypha.Apocrypha.models.User;
import com.Apocrypha.Apocrypha.service.UserKeycloakService;
import com.Apocrypha.Apocrypha.utils.Helper;
import com.Apocrypha.Apocrypha.utils.SuccessDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/keycloak")
public class KeycloakController {
    private final UserKeycloakService userKeycloakService;
    @Autowired
    public KeycloakController(UserKeycloakService userKeycloakService) {
        this.userKeycloakService = userKeycloakService;
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody RegisterDto registerDto) {
        try {
            userKeycloakService.registerUser(registerDto);
            return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
        } catch (UserAlreadyExistException e) {
            return new ResponseEntity<>("Username or email used", HttpStatus.CONFLICT);
        }
    }
    @GetMapping("/user")
    public ResponseEntity<User> getUser(Authentication authentication) {

        return new ResponseEntity<>(userKeycloakService.getUser(Helper.getKeycloakUser(authentication)), HttpStatus.OK);
    }
//    @PostMapping("/login")
//    public ResponseEntity loginUser(@RequestBody LoginDto loginDto){
//
//    }
}
