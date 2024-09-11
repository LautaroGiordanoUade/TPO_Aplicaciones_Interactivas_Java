package com.uade.grupo4.backend_ecommerce.repository.mapper;

import com.uade.grupo4.backend_ecommerce.controller.dto.ProductDto;
import com.uade.grupo4.backend_ecommerce.repository.entity.Product;

import java.util.List;

public class ProductMapper {

    public static ProductDto toDto(Product product){
        return new ProductDto(
                product.getId(),
                product.getTitle(),
                product.getDescription(),
                product.getCategoryId(),
                product.getQuantity(),
                product.getPrice()
        );
    }

    public static List<ProductDto> toDtoList(List<Product> products){
        return products.stream().map(
                product ->
                        new ProductDto(
                                product.getId(),
                                product.getTitle(),
                                product.getDescription(),
                                product.getCategoryId(),
                                product.getQuantity(),
                                product.getPrice()
                        )
                ).toList();
    }

    public static Product toEntity(ProductDto productDto){
        return new Product(
                productDto.getId(),
                null,
                productDto.getTitle(),
                productDto.getDescription(),
                productDto.getCategoryId(),
                productDto.getQuantity(),
                productDto.getPrice()
        );
    }

    public static List<Product> toEntityList(List<Product> productDtos){
        return productDtos.stream().map(
                productDto ->
                        new Product(
                                productDto.getId(),
                                null,
                                productDto.getTitle(),
                                productDto.getDescription(),
                                productDto.getCategoryId(),
                                productDto.getQuantity(),
                                productDto.getPrice()
                        )
        ).toList();
    }
}
