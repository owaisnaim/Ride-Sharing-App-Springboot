package com.example.ride.share.controllers;

import com.example.ride.share.entities.User;
import com.example.ride.share.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity addUser(String username, Integer age, String gender){
        userService.addUser(new User(username, age, gender));
        return new ResponseEntity(HttpStatus.OK);
    }
}
