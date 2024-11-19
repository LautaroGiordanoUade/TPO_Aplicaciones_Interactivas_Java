package com.uade.grupo4.backend_ecommerce.service.interfaces;

import com.uade.grupo4.backend_ecommerce.controller.dto.CategoryDto;

import java.util.List;

public interface CategoryServiceInterface {
    List<CategoryDto> getCategories();
    CategoryDto getById(Long id);
}
