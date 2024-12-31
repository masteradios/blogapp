package com.example.blogApp.services.category;

import com.example.blogApp.payloads.CategoryDto;
import  java.util.List;
public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
    void deleteCategory(Integer categoryId);
    List<CategoryDto> getAllCategories();
    CategoryDto getCategoryById(Integer categoryId);
}
