package com.blog.project.Blogproject.service;

import com.blog.project.Blogproject.payload.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto addCategory(CategoryDto categoryDto);

    CategoryDto getCategoryById(Long categoryId);

    List<CategoryDto> getAllCategories();

    CategoryDto updateCategory(CategoryDto categoryDto, Long id);

    String deleteCategoryById(Long id);
}
