package com.example.dlSpring.config;

import com.example.dlSpring.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Фильтр для проверки JWT токенов, применяется к каждому запросу.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    /**
     * Фильтрует каждый запрос для проверки наличия и валидности JWT токена.
     *
     * @param request     HTTP запрос.
     * @param response    HTTP ответ.
     * @param filterChain цепочка фильтров.
     * @throws ServletException если происходит ошибка сервлета.
     * @throws IOException      если происходит ошибка ввода-вывода.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Получение заголовка Authorization
        String authHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        // Проверка наличия и корректности заголовка Authorization
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            try {
                // Извлечение имени пользователя из токена
                username = jwtUtil.getUsernameFromToken(jwt);
            } catch (ExpiredJwtException e) {
                log.debug("Token lifetime has expired");
            } catch (SignatureException e) {
                log.debug("The signature is incorrect");
            }
        }

        // Проверка валидности токена и отсутствие аутентификации в контексте безопасности
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Создание токена аутентификации
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    jwtUtil.getRoleFromToken(jwt).stream().map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList())
            );
            // Установка аутентификации в контексте безопасности
            SecurityContextHolder.getContext().setAuthentication(token);
        }

        // Продолжение цепочки фильтров
        filterChain.doFilter(request, response);
    }
}
