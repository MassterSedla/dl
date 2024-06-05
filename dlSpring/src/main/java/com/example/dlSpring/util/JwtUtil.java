package com.example.dlSpring.util;

import com.example.dlSpring.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Утилитный класс для работы с JWT токенами.
 */
@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    private Duration lifetime;

    /**
     * Генерирует JWT токен для указанного пользователя.
     *
     * @param user пользователь, для которого генерируется токен.
     * @return сгенерированный JWT токен.
     */
    public String generatedToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        // Извлечение ролей пользователя и добавление их в claims
        List<String> rolesList = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("role", rolesList);

        // Установка времени выдачи и срока действия токена
        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + lifetime.toMillis());

        // Построение и возвращение JWT токена
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /**
     * Извлекает username из JWT токена.
     *
     * @param token JWT токен.
     * @return имя пользователя.
     */
    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    /**
     * Извлекает список ролей пользователя из JWT токена.
     *
     * @param token JWT токен.
     * @return список ролей пользователя.
     */
    public List<String> getRoleFromToken(String token) {
        return getAllClaimsFromToken(token).get("role", List.class);
    }

    /**
     * Извлекает все claims из JWT токена.
     *
     * @param token JWT токен.
     * @return объект Claims.
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}
