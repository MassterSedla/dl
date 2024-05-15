package com.example.dlSpring.service;

import com.example.dlSpring.dto.AuthorizedUserDto;
import com.example.dlSpring.model.User;
import com.example.dlSpring.repository.RoleRepository;
import com.example.dlSpring.repository.UserRepository;
import com.example.dlSpring.model.Role;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
//@NoArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       @Lazy BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //@Transactional
    public User saveUser(AuthorizedUserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.save(new Role(1L,"ROLE_USER"));
        roles.add(role);
        user.setRoles(roles);
        return userRepository.save(user);
    }

    //@Transactional
    public User getUser(Long id) {
        return userRepository.findById(id).get();
    }

    //@Transactional
    public User getUserByName(String name) {
        return userRepository.findByName(name);
    }


    @Override
    //@Transactional
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user;
        if ((user = userRepository.findByName(name)) == null) {
            throw new UsernameNotFoundException("User with name " + name + " not found");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                user.getAuthorities()
        );
    }


    public String getWeatherForecast() {
        return "It's gonna snow a lot. Brace yourselves, the winter is coming.";
    }
}