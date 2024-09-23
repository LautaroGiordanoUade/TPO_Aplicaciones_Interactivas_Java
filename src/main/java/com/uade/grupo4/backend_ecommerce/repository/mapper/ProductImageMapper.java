package com.uade.grupo4.backend_ecommerce.repository.mapper;

import com.uade.grupo4.backend_ecommerce.controller.dto.ProductImageDto;
import com.uade.grupo4.backend_ecommerce.repository.entity.ProductImage;

import java.util.List;

public class ProductImageMapper {

    public static ProductImageDto toDto(ProductImage productImage) {
        return new ProductImageDto(
                productImage.getId(),
                productImage.getProduct().getId(),
                productImage.getImageBase64()
        );
    }

    public static List<ProductImageDto> toDtoList(List<ProductImage> productImages) {
        return productImages.stream().map(
                ProductImageMapper::toDto
        ).toList();
    }

    public static ProductImage toEntity(ProductImageDto productImageDto) {
        return new ProductImage(
                null,
                null,
                productImageDto.getImageBase64()
        );
    }

    public static List<ProductImage> toEntityList(List<ProductImageDto> productImageDtos) {
        return productImageDtos.stream().map(
                ProductImageMapper::toEntity
        ).toList();
    }
}
