package com.example.blogApp.services.user;

import com.example.blogApp.payloads.UserDto;
import com.example.blogApp.payloads.UserResponse;

import java.util.List;
public interface UserService {

    UserDto signUpUser(UserDto userDto);
    UserResponse loginUser(String email,String password);
    UserDto getUserById(String userId);
    List<UserDto> getAllUsers();
    UserDto updateUser(UserDto userDto,String userId);
    void deleteUser(String userId);

}
