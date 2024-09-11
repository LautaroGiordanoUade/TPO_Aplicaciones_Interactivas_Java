package com.uade.grupo4.backend_ecommerce.service.implementations;

import com.uade.grupo4.backend_ecommerce.repository.ProductRepository;
import com.uade.grupo4.backend_ecommerce.repository.entity.Product;
import com.uade.grupo4.backend_ecommerce.controller.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<ProductDto> listProducts() {
        final List<Product> products = productRepository.listProducts();
        return products.stream().map(product ->
                        new ProductDto(product.getId(), product.getTitle(), product.getDescription(), product.getCategoryId(), product.getQuantity())
                )
                .toList();
    }

    public ProductDto findById(Long id) throws Exception {
        final Product product = productRepository.findById(id).orElseThrow(() -> new Exception("An error has occurred gettin the product with id " + id));
        return new ProductDto(product.getId(), product.getTitle(), product.getDescription(), product.getCategoryId(), product.getQuantity());
    }

    public List<ProductDto> findByUser(Long userId) {
        final List<Product> products = productRepository.findByUser(userId);
        return products.stream().map(product ->
                        new ProductDto(product.getId(), product.getTitle(), product.getDescription(), product.getCategoryId(), product.getQuantity())
                )
                .toList();
    }
}
