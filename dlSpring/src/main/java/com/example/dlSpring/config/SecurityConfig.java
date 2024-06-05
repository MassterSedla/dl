package com.example.dlSpring.config;

import com.example.dlSpring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Конфигурационный класс для настройки безопасности Spring Security.
 */
@Configuration
@EnableAspectJAutoProxy
public class SecurityConfig {
    private UserService userService;
    private JwtRequestFilter jwtRequestFilter;

    /**
     * Устанавливает UserService через инъекцию зависимостей.
     *
     * @param userService сервис для работы с пользователями.
     */
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Устанавливает JwtRequestFilter через инъекцию зависимостей.
     *
     * @param jwtRequestFilter фильтр для обработки JWT запросов.
     */
    @Autowired
    public void setJwtRequestFilter(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    /**
     * Создает и возвращает BCryptPasswordEncoder для хэширования паролей.
     *
     * @return экземпляр BCryptPasswordEncoder.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Настраивает фильтры безопасности и правила доступа для HTTP запросов,
     * утсанавливает дополнительно jwtRequestFilter.
     *
     * @param http объект для настройки HTTP безопасности.
     * @return настроенная цепочка фильтров безопасности.
     * @throws Exception если происходит ошибка при настройке.
     */
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeRequests(auth -> auth
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/user/**").hasRole("USER")
                        .requestMatchers("/api/**").hasRole("USER")
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Создает и настраивает DaoAuthenticationProvider на основе userService
     * для аутентификации пользователей.
     *
     * @return настроенный DaoAuthenticationProvider.
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userService);
        return daoAuthenticationProvider;
    }

    /**
     * Создает и возвращает AuthenticationManager для управления аутентификацией.
     *
     * @param authenticationConfiguration конфигурация для аутентификации.
     * @return экземпляр AuthenticationManager.
     * @throws Exception если происходит ошибка при создании.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
