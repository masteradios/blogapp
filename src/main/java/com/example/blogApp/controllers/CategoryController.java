package com.example.blogApp.controllers;

import com.example.blogApp.payloads.CategoryDto;
import com.example.blogApp.payloads.ApiResponse;
import com.example.blogApp.services.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")

public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(categoryDto));
    }

    @PostMapping("/update/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable Integer categoryId)
    {
        return ResponseEntity.ok(categoryService.updateCategory(categoryDto,categoryId));
    }

    @GetMapping("/getById/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer categoryId){
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }
    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId)
    {
        categoryService.deleteCategory(categoryId);
        ApiResponse apiResponse=new ApiResponse("Deleted Successfully!!",true);
        return  ResponseEntity.ok(apiResponse);
    }
}
