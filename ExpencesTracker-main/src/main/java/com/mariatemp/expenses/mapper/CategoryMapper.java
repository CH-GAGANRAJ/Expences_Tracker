package com.mariatemp.expenses.mapper;

import com.mariatemp.expenses.dto.CategoryDto;
import com.mariatemp.expenses.model.Category;

public class CategoryMapper {

    public static CategoryDto toDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }

    public static Category toEntity(CategoryDto dto) {
        Category category = new Category();
        category.setName(dto.getName());
        return category;
    }
}
