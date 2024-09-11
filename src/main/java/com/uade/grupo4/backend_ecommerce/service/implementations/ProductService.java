package com.uade.grupo4.backend_ecommerce.service.implementations;

import com.uade.grupo4.backend_ecommerce.repository.ProductRepository;
import com.uade.grupo4.backend_ecommerce.repository.entity.Product;
import com.uade.grupo4.backend_ecommerce.controller.dto.ProductDto;
import com.uade.grupo4.backend_ecommerce.repository.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<ProductDto> listProducts() {
        final List<Product> products = productRepository.listProducts();
        return ProductMapper.toDtoList(products);
    }

    public ProductDto findById(Long id) throws Exception {
        final Product product = productRepository.findById(id).orElseThrow(() -> new Exception("An error has occurred gettin the product with id " + id));
        return ProductMapper.toDto(product);
    }

    public List<ProductDto> findByUser(Long userId) {
        final List<Product> products = productRepository.findByUser(userId);
        return ProductMapper.toDtoList(products);
    }
}
