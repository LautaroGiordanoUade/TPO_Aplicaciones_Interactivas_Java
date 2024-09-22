package com.uade.grupo4.backend_ecommerce.controller;

import com.uade.grupo4.backend_ecommerce.exception.ResourceNotFoundException;
import com.uade.grupo4.backend_ecommerce.service.implementations.ProductService;
import com.uade.grupo4.backend_ecommerce.controller.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/admin")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto createdProduct = productService.saveProduct(productDto);
        return ResponseEntity.created(URI.create("/api/v1/product/" + createdProduct.getId())).body(createdProduct);
    }

    @PutMapping("/admin")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto) {
        ProductDto updatedProduct = productService.updateProduct(productDto);

        if (updatedProduct == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        final List<ProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        try {
            final ProductDto product = productService.getById(id);
            return ResponseEntity.ok(product);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/admin")
    public ResponseEntity<List<ProductDto>> getProductByUserId() {
        final List<ProductDto> products = productService.getByUserId();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<ProductDto>> getProductByCategoryId(@PathVariable Long id) {
        final List<ProductDto> products = productService.getByCategoryId(id);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/featured")
    public ResponseEntity<List<ProductDto>> getFeturedProducts() {
        final List<ProductDto> products = productService.getFeaturedProducts();
        return ResponseEntity.ok(products);
    }

    @PostMapping("/user/favorite/{id}")
    public ResponseEntity<?> addFavorite(@PathVariable Long id) {
        try {
            ProductDto productDto = productService.addFavorite(id);
            return ResponseEntity.created(URI.create("/api/v1/product/" + id)).body(productDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/user/favorite/{id}")
    public ResponseEntity<?> removeFavorite(@PathVariable Long id) {
        try {
            productService.removeFavorite(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/user/favorites")
    public ResponseEntity<List<ProductDto>> getFavorites() {
        List<ProductDto> favoriteProducts = productService.getFavorites();
        return ResponseEntity.ok(favoriteProducts);
    }

    @GetMapping("/user/viewed")
    public ResponseEntity<List<ProductDto>> getViewed() {
        List<ProductDto> favoriteProducts = productService.getViewed();
        return ResponseEntity.ok(favoriteProducts);
    }
}
