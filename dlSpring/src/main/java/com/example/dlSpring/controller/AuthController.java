package com.example.dlSpring.controller;

import com.example.dlSpring.dto.AuthorizedUserDto;
import com.example.dlSpring.model.User;
import com.example.dlSpring.service.AuthService;
import com.example.dlSpring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody AuthorizedUserDto authorizedUserDto) {
        return authService.createAuthToken(authorizedUserDto);
    }

    @GetMapping("/user/info")
    public ResponseEntity<User> getUserInfo(Principal principal) {
        return new  ResponseEntity<>(userService.getUserByUsername(principal.getName()), HttpStatus.OK);
    }

}
