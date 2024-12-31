package com.example.blogApp.config;

import com.example.blogApp.models.Role;
import com.example.blogApp.repositories.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {


    @Autowired
    private RoleRepo roleRepository;


    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findByRole("ROLE_USER").isEmpty()){
            Role userRole = new Role();
            userRole.setRole("ROLE_USER");
            roleRepository.save(userRole);
        }

        if (roleRepository.findByRole("ROLE_ADMIN").isEmpty()) {
            Role adminRole = new Role();
            adminRole.setRole("ROLE_ADMIN");
            roleRepository.save(adminRole);
        }
    }
}
