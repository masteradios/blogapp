package com.example.blogApp.services.category;

import com.example.blogApp.exceptions.ResourceNotFoundException;
import com.example.blogApp.models.CategoryModel;
import com.example.blogApp.payloads.CategoryDto;
import com.example.blogApp.repositories.CategoryRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryImpl implements CategoryService{

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        CategoryModel categoryModel = modelMapper.map(categoryDto, CategoryModel.class);
        CategoryModel savedCategory = categoryRepo.save(categoryModel);
        return modelMapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        CategoryModel categoryModel = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category with this Id not found"));
        categoryModel.setCategoryTitle(categoryDto.getCategoryTitle());
        categoryModel.setCategoryDescription(categoryDto.getCategoryDescription());
        CategoryModel savedCategory = categoryRepo.save(categoryModel);

        return modelMapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        CategoryModel categoryModel = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category with this Id not found"));
categoryRepo.delete(categoryModel);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<CategoryDto> categoryDtos = categoryRepo.findAll().stream().map((category) -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());

        return categoryDtos;
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        CategoryModel categoryModel = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category with this Id not found"));

        return modelMapper.map(categoryModel,CategoryDto.class);
    }
}
