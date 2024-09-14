package com.uade.grupo4.backend_ecommerce.repository.mapper;

import com.uade.grupo4.backend_ecommerce.controller.dto.CategoryDto;
import com.uade.grupo4.backend_ecommerce.repository.entity.Category;

import java.util.List;

public class CategoryMapper {

    public static CategoryDto toDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName()
        );
    }

    public static List<CategoryDto> toDtoList(List<Category> categories) {
        return categories.stream().map(
                category ->
                        new CategoryDto(
                                category.getId(),
                                category.getName()
                        )
        ).toList();
    }

    public static Category toEntity(CategoryDto categoryDto) {
        return new Category(
                categoryDto.getId(),
                categoryDto.getName()
        );
    }

    public static List<Category> toEntityList(List<CategoryDto> categoryDtos) {
        return categoryDtos.stream().map(
                categoryDto ->
                        new Category(
                                categoryDto.getId(),
                                categoryDto.getName()
                        )
        ).toList();
    }
}
