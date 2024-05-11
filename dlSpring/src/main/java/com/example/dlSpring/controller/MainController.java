package com.example.dlSpring.controller;

import com.example.dlSpring.dto.AuthorizedUserDto;
import com.example.dlSpring.model.User;
import com.example.dlSpring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MainController {
    private final UserService service;


    @Autowired
    public MainController(UserService service) {
        this.service = service;
    }


    @GetMapping("/page/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = service.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<?> newUser() {

        return new ResponseEntity<>("", HttpStatus.OK);
    }


}