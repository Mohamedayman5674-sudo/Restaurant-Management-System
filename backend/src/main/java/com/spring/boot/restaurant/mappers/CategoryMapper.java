package com.spring.boot.restaurant.mappers;

import com.spring.boot.restaurant.dto.CategoryDto;
import com.spring.boot.restaurant.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {
    CategoryMapper CATEGORY_MAPPER = Mappers.getMapper(CategoryMapper.class);

    CategoryDto toCategoryDto(Category category);

    Category toCategory(CategoryDto categoryDto);

}
