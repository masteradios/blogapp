package com.example.blogApp.controllers;

import com.example.blogApp.payloads.UserDto;
import com.example.blogApp.payloads.UserResponse;
import com.example.blogApp.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {


    @Autowired
    @Qualifier("UserServiceImpl")
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser(@RequestBody UserDto userDto){
        UserDto savedUser = userService.signUpUser(userDto);
        return ResponseEntity.ok(savedUser);

    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password")String password){

        UserResponse userResponse = userService.loginUser(email, password);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userResponse);

    }

}
