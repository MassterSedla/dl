package com.example.dlSpring.controller;

import com.example.dlSpring.dto.AuthorizedUserDto;
import com.example.dlSpring.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody AuthorizedUserDto authorizedUserDto) {
        System.out.println("penis");
        return authService.createAuthToken(authorizedUserDto);
    }

}
