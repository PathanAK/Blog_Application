package com.blog.project.Blogproject.service.impl;

import com.blog.project.Blogproject.entity.Category;
import com.blog.project.Blogproject.exception.ResourceNotFoundException;
import com.blog.project.Blogproject.payload.CategoryDto;
import com.blog.project.Blogproject.respository.CategoryRepository;
import com.blog.project.Blogproject.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper mapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {

        Category category = mapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return mapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                                              .orElseThrow(()->new ResourceNotFoundException("Category","id",categoryId));
        return mapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {

        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> mapper.map(category, CategoryDto.class))
                                                            .collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","id",categoryId));
        if (categoryDto.getName() != null) {
            existingCategory.setName(categoryDto.getName());
        }
        if (categoryDto.getDescription() != null) {
            existingCategory.setDescription(categoryDto.getDescription());
        }
        Category saveUpdatedCategory = categoryRepository.save(existingCategory);
        return mapper.map(saveUpdatedCategory, CategoryDto.class);
    }

    @Override
    public String deleteCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","id",categoryId));
        categoryRepository.deleteById(categoryId);
        return "Category deleted with " + categoryId;
    }
}
