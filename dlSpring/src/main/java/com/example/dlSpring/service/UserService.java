package com.example.dlSpring.service;

import com.example.dlSpring.dto.AuthorizedUserDto;
import com.example.dlSpring.model.User;
import com.example.dlSpring.repository.RoleRepository;
import com.example.dlSpring.repository.UserRepository;
import com.example.dlSpring.model.Role;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Сервисный класс для работы с пользователями и аутентификации.
 * Реализует интерфейс UserDetailsService для интеграции с Spring Security.
 */
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Конструктор с зависимостями, отмеченные @Lazy - для отложенной инициализации.
     * @param userRepository репозиторий пользователей
     * @param roleRepository репозиторий ролей
     * @param passwordEncoder энкодер для паролей
     */
    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       @Lazy BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Сохраняет нового пользователя в базе данных.
     * @param userDto DTO с данными пользователя
     * @return сохраненный пользователь
     */
    @Transactional
    public User saveUser(AuthorizedUserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.save(new Role(1L, "ROLE_USER"));
        roles.add(role);
        user.setRoles(roles);
        return userRepository.save(user);
    }

    /**
     * Возвращает пользователя по его id.
     * @param id идентификатор пользователя
     * @return найденный пользователь
     */
    @Transactional
    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Возвращает пользователя по его username.
     * @param username имя пользователя
     * @return найденный пользователь
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Загружает пользователя по его username для Spring Security.
     * @param username имя пользователя
     * @return детали пользователя для аутентификации
     * @throws UsernameNotFoundException если пользователь не найден
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        if ((user = userRepository.findByUsername(username)) == null) {
            throw new UsernameNotFoundException("User with username " + username + " not found");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
        );
    }
}
