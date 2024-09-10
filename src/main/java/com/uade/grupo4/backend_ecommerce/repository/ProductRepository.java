package com.uade.grupo4.backend_ecommerce.repository;

import com.uade.grupo4.backend_ecommerce.repository.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ProductRepository {

    private final List<Product> products = new ArrayList<>();

    public ProductRepository() {
        products.add(new Product(1L, 1L, "Samsung A54", "Celular BBB", 1L, 100));
        products.add(new Product(2L, 1L, "Samsung S24", "Celular gama alta", 1L, 30));
    }

    public List<Product> listProducts() {
        return products;
    }

    public Optional<Product> findById(Long id) {
        return products.stream()
                .filter(product -> Objects.equals(product.getId(), id))
                .findFirst();
    }

    public List<Product> findByUser(Long userId) {
        return products.stream()
                .filter(product -> Objects.equals(product.getUserId(), userId))
                .toList();
    }
}
