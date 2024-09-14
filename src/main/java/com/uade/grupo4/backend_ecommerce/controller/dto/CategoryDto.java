package com.uade.grupo4.backend_ecommerce.controller.dto;

import com.uade.grupo4.backend_ecommerce.repository.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
}
