package com.uade.grupo4.backend_ecommerce.repository.mapper;

import com.uade.grupo4.backend_ecommerce.controller.dto.ProductDto;
import com.uade.grupo4.backend_ecommerce.repository.entity.Category;
import com.uade.grupo4.backend_ecommerce.repository.entity.Product;

import java.util.HashSet;
import java.util.List;

public class ProductMapper {

    public static ProductDto toDto(Product product) {
        if (product == null) {
            return null;
        }
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategory().getId(),
                product.getQuantity(),
                product.getPrice(),
                product.isFeatured(),
                product.isFavorite(),
                ProductImageMapper.toDtoList(product.getImages())
        );
    }

    public static List<ProductDto> toDtoList(List<Product> products) {
        return products.stream().map(
                ProductMapper::toDto
        ).toList();
    }

    public static Product toEntity(ProductDto productDto) {
        return new Product(
                productDto.getId(),
                null,
                productDto.getName(),
                productDto.getDescription(),
                new Category(productDto.getCategoryId(), null),
                productDto.getQuantity(),
                productDto.getPrice(),
                productDto.isFeatured(),
                new HashSet<>(),
                new HashSet<>(),
                ProductImageMapper.toEntityList(productDto.getImages()),
                productDto.isFavorite()
        );
    }
}
