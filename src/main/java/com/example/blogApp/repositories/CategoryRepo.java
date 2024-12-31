package com.example.blogApp.repositories;

import com.example.blogApp.models.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<CategoryModel,Integer> {
}
