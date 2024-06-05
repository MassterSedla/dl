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

/**
 * Сервисный класс для аутентификации пользователей.
 */
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    /**
     * Создает токен аутентификации для пользователя.
     * @param authorizedUserDto DTO с данными пользователя для аутентификации.
     * @return ResponseEntity с JWT токеном в случае успешной аутентификации или ошибкой при неудаче.
     */
    public ResponseEntity<?> createAuthToken(AuthorizedUserDto authorizedUserDto) {
        try {
            // Аутентификация пользователя с использованием менеджера аутентификации.
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authorizedUserDto.getUsername(), authorizedUserDto.getPassword()));
        } catch (BadCredentialsException e) {
            // Возвращает ошибку при некорректных учетных данных.
            return new ResponseEntity<>(new AuthException(HttpStatus.UNAUTHORIZED.value(),
                    "Incorrect login or password"), HttpStatus.UNAUTHORIZED);
        }

        // Получает пользователя по его username.
        User user = userService.getUserByUsername(authorizedUserDto.getUsername());
        // Генерирует JWT токен для пользователя.
        String token = jwtUtil.generatedToken(user);
        // Устанавливает аутентификацию в контексте безопасности.
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        null,
                        user.getAuthorities()
                )
        );
        // Возвращает ResponseEntity с токеном.
        return ResponseEntity.ok(token);
    }
}
