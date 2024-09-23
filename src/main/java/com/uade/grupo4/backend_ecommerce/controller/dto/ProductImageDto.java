package com.uade.grupo4.backend_ecommerce.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductImageDto {
    private Long id;
    private Long productId;
    private String imageBase64;
}
