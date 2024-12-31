package com.example.blogApp.repositories;

import com.example.blogApp.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository

public interface RoleRepo extends JpaRepository<Role,Integer> {

    Optional<Role> findByRole(String role);
}
