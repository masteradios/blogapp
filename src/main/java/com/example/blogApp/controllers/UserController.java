package com.example.blogApp.controllers;

import com.example.blogApp.payloads.UserDto;
import com.example.blogApp.payloads.ApiResponse;
import com.example.blogApp.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    @Qualifier("UserServiceImpl")
    private UserService userService;


    @PostMapping("/update/{userId}")
    public ResponseEntity<?> updateUserDetails(@RequestBody UserDto userDto, @PathVariable String userId){

        UserDto updateUser = userService.updateUser(userDto, userId);
        return ResponseEntity.ok(updateUser);

    }
    @PostMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId)
    {
        userService.deleteUser(userId);
        ApiResponse apiResponse=new ApiResponse("Deleted Sucessfully!!",true);
        return  ResponseEntity.ok(apiResponse);
    }
    @GetMapping("")
    public ResponseEntity<List<UserDto>> getAllUsers(){

        return ResponseEntity.ok(userService.getAllUsers());
    }
    @GetMapping("/getUser/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId){
        return ResponseEntity.ok(userService.getUserById(userId));
    }
}
