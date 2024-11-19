package com.uade.grupo4.backend_ecommerce.service.interfaces;

import com.uade.grupo4.backend_ecommerce.controller.dto.ProductDto;

import java.util.List;

public interface ProductServiceInterface {
    ProductDto saveProduct(ProductDto productDto);
    ProductDto updateProduct(ProductDto productDto);
    void deleteProduct(Long id);
    List<ProductDto> getProducts(String search);
    ProductDto getById(Long id);
    List<ProductDto> getByUserId();
    List<ProductDto> getByCategoryId(Long categoryId);
    List<ProductDto> getFeaturedProducts();
    ProductDto addFavorite(Long productId);
    void removeFavorite(Long productId);
    List<ProductDto> getFavorites();
    List<ProductDto> getViewed();
}
