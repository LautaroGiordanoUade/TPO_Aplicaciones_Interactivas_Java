package com.uade.grupo4.backend_ecommerce.service.implementations;

import com.uade.grupo4.backend_ecommerce.controller.dto.CategoryDto;
import com.uade.grupo4.backend_ecommerce.exception.ResourceNotFoundException;
import com.uade.grupo4.backend_ecommerce.repository.CategoryRepository;
import com.uade.grupo4.backend_ecommerce.repository.entity.*;
import com.uade.grupo4.backend_ecommerce.repository.mapper.CategoryMapper;
import com.uade.grupo4.backend_ecommerce.service.interfaces.CategoryServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements CategoryServiceInterface {

    @Autowired
    CategoryRepository categoryRepository;

    public List<CategoryDto> getCategories() {
        final List<Category> categories = categoryRepository.findAll();
        return CategoryMapper.toDtoList(categories);
    }

    public CategoryDto getById(final Long id) {
        final Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la categoría."));

        return CategoryMapper.toDto(category);
    }
}
