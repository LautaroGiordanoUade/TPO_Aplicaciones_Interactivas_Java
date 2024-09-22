package com.uade.grupo4.backend_ecommerce.service.implementations;

import com.uade.grupo4.backend_ecommerce.exception.ResourceNotFoundException;
import com.uade.grupo4.backend_ecommerce.repository.FavoriteProductRepository;
import com.uade.grupo4.backend_ecommerce.repository.ProductRepository;
import com.uade.grupo4.backend_ecommerce.repository.entity.FavoriteProduct;
import com.uade.grupo4.backend_ecommerce.repository.entity.Product;
import com.uade.grupo4.backend_ecommerce.controller.dto.ProductDto;
import com.uade.grupo4.backend_ecommerce.repository.entity.User;
import com.uade.grupo4.backend_ecommerce.repository.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class ProductService {

    @Autowired
    UserService userService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    FavoriteProductRepository favoriteProductRepository;

    public ProductDto saveProduct(ProductDto productDto) {
        Product product = ProductMapper.toEntity(productDto);
        product.setUser(userService.getLoggedUser());
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toDto(savedProduct);
    }

    public ProductDto updateProduct(ProductDto productDto) {
        final Product currentProduct = productRepository.findById(productDto.getId()).orElse(null);
        if (currentProduct == null) {
            return null;
        }
        Product product = ProductMapper.toEntity(productDto);
        product.setUser(userService.getLoggedUser());
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
        final Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el producto."));
        return ProductMapper.toDto(product);
    }

    public List<ProductDto> getByUserId() {
        Long userId = userService.getLoggedUser().getId();
        final List<Product> products = productRepository.findByUserId(userId);
        return ProductMapper.toDtoList(products);
    }

    public List<ProductDto> getByCategoryId(Long categoryId) {
        final List<Product> products = productRepository.findByCategoryId(categoryId);
        return ProductMapper.toDtoList(products);
    }

    public List<ProductDto> getFeaturedProducts() {
        final List<Product> products = productRepository.findByFeatured(true);
        return ProductMapper.toDtoList(products);
    }

    public ProductDto addFavorite(Long productId) {
        User user = userService.getLoggedUser();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el producto."));

        if(favoriteProductRepository.findByUserIdAndProductId(user.getId(), productId).isEmpty()) {
            FavoriteProduct favoriteProduct = new FavoriteProduct();
            favoriteProduct.setProduct(product);
            favoriteProduct.setUser(user);
            favoriteProduct.setAddedDate(LocalDateTime.now());
            favoriteProductRepository.save(favoriteProduct);
        }

        return ProductMapper.toDto(product);
    }

    public void removeFavorite(Long productId) {
        Long userId = userService.getLoggedUser().getId();
        final FavoriteProduct favoriteProduct = favoriteProductRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el elemento guardado como favorito."));
        favoriteProductRepository.delete(favoriteProduct);
    }

    public List<ProductDto> getFavorites() {
        Long userId = userService.getLoggedUser().getId();
        final Set<FavoriteProduct> favoriteProducts = favoriteProductRepository.findByUserId(userId);
        final List<Product> products = favoriteProducts.stream().map(FavoriteProduct::getProduct).toList();
        return ProductMapper.toDtoList(products);
    }
}
