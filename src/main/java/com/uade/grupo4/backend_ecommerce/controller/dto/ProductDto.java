package com.uade.grupo4.backend_ecommerce.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private Long categoryId;
    private int quantity;
    private Long price;
    private boolean featured;
}
