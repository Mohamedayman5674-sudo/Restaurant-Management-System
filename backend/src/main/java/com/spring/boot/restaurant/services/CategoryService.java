package com.spring.boot.restaurant.services;

import com.spring.boot.restaurant.controllers.vm.CategoryResponseVm;
import com.spring.boot.restaurant.dto.CategoryDto;

import java.time.LocalDateTime;
import java.util.List;

public interface CategoryService {

    CategoryResponseVm getAllCategories(int page, int size);

    CategoryDto createCategory(CategoryDto categoryDto);

    List<CategoryDto> createListOfCategory(List<CategoryDto> categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto);

    List<CategoryDto> updateListOfCategory(List<CategoryDto> categoryDto);

    void deleteCategoryById(Long id);

    void deleteListOfCategory(List<Long> categoryIds);

    CategoryDto getCategoryById(Long id);

    CategoryResponseVm searchCategories(
            LocalDateTime createdFrom,
            LocalDateTime createdTo,
            LocalDateTime updatedFrom,
            LocalDateTime updatedTo,
            String createdBy,
            String updatedBy,
            int page,
            int size
    );
}