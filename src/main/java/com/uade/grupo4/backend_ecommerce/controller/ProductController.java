package com.uade.grupo4.backend_ecommerce.controller;

import com.uade.grupo4.backend_ecommerce.exception.NotOwnerException;
import com.uade.grupo4.backend_ecommerce.exception.ResourceNotFoundException;
import com.uade.grupo4.backend_ecommerce.controller.dto.ProductDto;
import com.uade.grupo4.backend_ecommerce.exception.ValidationException;
import com.uade.grupo4.backend_ecommerce.service.interfaces.ProductServiceInterface;
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
    ProductServiceInterface productService;

    @PostMapping("/admin")
    public ResponseEntity<?> createProduct(@RequestBody ProductDto productDto) {
        try {
            ProductDto createdProduct = productService.saveProduct(productDto);
            return ResponseEntity.created(URI.create("/api/v1/product/" + createdProduct.getId())).body(createdProduct);
        } catch (ResourceNotFoundException | ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/admin")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDto productDto) {
        try {
            ProductDto updatedProduct = productService.updateProduct(productDto);
            return ResponseEntity.ok(updatedProduct);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NotOwnerException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (NotOwnerException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts(@RequestParam(required = false) String search) {
        final List<ProductDto> products = productService.getProducts(search);
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
    public ResponseEntity<List<ProductDto>> getFeaturedProducts() {
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
