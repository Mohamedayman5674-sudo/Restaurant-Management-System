package com.spring.boot.restaurant.controllers.vm;

import com.spring.boot.restaurant.dto.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseVm {

    private List<CategoryDto> categories;
    private Long totalCategories;
}