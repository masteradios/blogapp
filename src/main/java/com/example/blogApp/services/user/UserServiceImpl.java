package com.example.blogApp.services.user;

import com.example.blogApp.exceptions.ResourceNotFoundException;
import com.example.blogApp.models.Role;
import com.example.blogApp.models.UserModel;
import com.example.blogApp.payloads.UserDto;
import com.example.blogApp.payloads.UserResponse;
import com.example.blogApp.repositories.RoleRepo;
import com.example.blogApp.repositories.UserRepo;
import com.example.blogApp.security.CustomUserDetailsService;
import com.example.blogApp.security.jwt.JwtUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UserDto signUpUser(UserDto userDto) {

        if (userRepo.findByEmail(userDto.getEmail()).isPresent()) {

            throw new RuntimeException("User Already exists");

        }

        userDto.setId(UUID.randomUUID().toString());
        System.out.println(userDto);
        UserModel user = modelMapper.map(userDto, UserModel.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role userRole = roleRepo.findByRole("ROLE_USER").orElseThrow(() -> new ResourceNotFoundException("No such Role exists!!"));
        user.getRoles().add(userRole);
        UserModel savedUser = userRepo.save(user);

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserResponse loginUser(String email, String password) {
        UserDetails user = userDetailsService.loadUserByUsername(email);
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password, user.getAuthorities()));
        String accessToken = jwtUtils.generateJwtFromEmail(user, 30 * 60 * 1000);


        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserResponse userResponse = new UserResponse();
        userResponse.setUser(modelMapper.map(user, UserDto.class));
        userResponse.setJwtToken(accessToken);
        return userResponse;
    }


    @Override
    public UserDto getUserById(String userId) {
        UserModel userModel = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with this Id not found"));

        return modelMapper.map(userModel, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<UserDto> userDtoList = userRepo.findAll().stream().map((user) -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());

        return userDtoList;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        UserModel userModel = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with this Id not found"));


        userModel.setUsername(userDto.getUsername());
        userModel.setAbout(userDto.getAbout());
        UserModel savedUser = userRepo.save(userModel);
        return modelMapper.map(savedUser, UserDto.class);

    }

    @Override
    public void deleteUser(String userId) {
        UserModel userModel = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with this Id not found"));
        userRepo.delete(userModel);
    }
}
