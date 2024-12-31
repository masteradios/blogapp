package com.example.blogApp.repositories;

import com.example.blogApp.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserModel,String> {

    Optional<UserModel> findByEmail(String email);

}
