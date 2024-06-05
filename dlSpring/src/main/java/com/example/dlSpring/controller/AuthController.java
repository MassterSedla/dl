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

/**
 * Контроллер для обработки запросов, связанных с аутентификацией и информацией о пользователе.
 */
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    /**
     * Обрабатывает POST-запрос на '/login' для создания токена аутентификации.
     *
     * @param authorizedUserDto DTO, содержащий данные для аутентификации (имя пользователя и пароль).
     * @return ResponseEntity с токеном аутентификации или сообщением об ошибке.
     */
    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody AuthorizedUserDto authorizedUserDto) {
        return authService.createAuthToken(authorizedUserDto);
    }

    /**
     * Обрабатывает GET-запрос на '/user/info' для получения информации о текущем аутентифицированном пользователе.
     *
     * @param principal объект Principal, представляющий текущего аутентифицированного пользователя.
     * @return ResponseEntity с информацией о пользователе и статусом OK.
     */
    @GetMapping("/user/info")
    public ResponseEntity<User> getUserInfo(Principal principal) {
        return new ResponseEntity<>(userService.getUserByUsername(principal.getName()), HttpStatus.OK);
    }
}
