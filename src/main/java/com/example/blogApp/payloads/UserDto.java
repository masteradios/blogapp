package com.example.blogApp.payloads;

import com.example.blogApp.models.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class UserDto {
    private String id;
    private  String username;
    private String email;
    @JsonProperty(access = Access.WRITE_ONLY)
    private  String password;
    private  String about;
    private List<RoleDto> roles =new ArrayList<>();
    public UserDto(String id, String username, String email, String password, String about) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.about = about;
    }

    public UserDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", about='" + about + '\'' +
                '}';
    }

    public List<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }
}
