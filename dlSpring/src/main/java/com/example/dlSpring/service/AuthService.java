package com.example.dlSpring.service;

import com.example.dlSpring.dto.AuthorizedUserDto;
import com.example.dlSpring.exception.AuthException;
import com.example.dlSpring.model.User;
import com.example.dlSpring.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> createAuthToken(AuthorizedUserDto authorizedUserDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authorizedUserDto.getUsername(), authorizedUserDto.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AuthException(HttpStatus.UNAUTHORIZED.value(),
                    "Incorrect login or password"), HttpStatus.UNAUTHORIZED);
        }

        User user = userService.getUserByUsername(authorizedUserDto.getUsername());
        String token = jwtUtil.generatedToken(user);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        null,
                        user.getAuthorities()
                )
        );
        return ResponseEntity.ok(token);
    }
}