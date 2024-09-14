package com.uade.grupo4.backend_ecommerce.service.implementations;

import com.uade.grupo4.backend_ecommerce.repository.ProductRepository;
import com.uade.grupo4.backend_ecommerce.repository.UserRepository;
import com.uade.grupo4.backend_ecommerce.repository.entity.Product;
import com.uade.grupo4.backend_ecommerce.controller.dto.ProductDto;
import com.uade.grupo4.backend_ecommerce.repository.entity.User;
import com.uade.grupo4.backend_ecommerce.repository.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    public ProductDto saveProduct(ProductDto productDto) {
        Product product = ProductMapper.toEntity(productDto);
        // TODO: get user id from authentication
        //Long userId = SecurityContextHolder.getContext().getAuthentication().getId();
        //product.setUser(userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found")));
        product.setUser(new User(1, "", "", "", null, "", ""));
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toDto(savedProduct);
    }

    public ProductDto updateProduct(ProductDto productDto) {
        final Product currentProduct = productRepository.findById(productDto.getId()).orElse(null);
        if (currentProduct == null) {
            return null;
        }
        Product product = ProductMapper.toEntity(productDto);
        // TODO: get user id from authentication
        //Long userId = SecurityContextHolder.getContext().getAuthentication().getId();
        //product.setUser(userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found")));
        product.setUser(new User(1, "", "", "", null, "", ""));
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toDto(savedProduct);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<ProductDto> getAllProducts() {
        final List<Product> products = productRepository.findAll();
        return ProductMapper.toDtoList(products);
    }

    public ProductDto getById(Long id) {
        final Product product = productRepository.findById(id).orElse(null);
        return ProductMapper.toDto(product);
    }

    public List<ProductDto> getByUserId(Long userId) {
        final List<Product> products = productRepository.findByUserId(userId);
        return ProductMapper.toDtoList(products);
    }

    public List<ProductDto> getByCategoryId(Long categoryId) {
        final List<Product> products = productRepository.findByCategoryId(categoryId);
        return ProductMapper.toDtoList(products);
    }
}
