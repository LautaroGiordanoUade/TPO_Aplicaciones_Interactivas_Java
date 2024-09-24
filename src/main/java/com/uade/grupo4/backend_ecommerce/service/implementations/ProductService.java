package com.uade.grupo4.backend_ecommerce.service.implementations;

import com.uade.grupo4.backend_ecommerce.exception.NotOwnerException;
import com.uade.grupo4.backend_ecommerce.exception.ResourceNotFoundException;
import com.uade.grupo4.backend_ecommerce.exception.ValidationException;
import com.uade.grupo4.backend_ecommerce.repository.CategoryRepository;
import com.uade.grupo4.backend_ecommerce.repository.FavoriteProductRepository;
import com.uade.grupo4.backend_ecommerce.repository.ProductRepository;
import com.uade.grupo4.backend_ecommerce.repository.ViewedProductRepository;
import com.uade.grupo4.backend_ecommerce.repository.entity.*;
import com.uade.grupo4.backend_ecommerce.controller.dto.ProductDto;
import com.uade.grupo4.backend_ecommerce.repository.mapper.ProductMapper;
import com.uade.grupo4.backend_ecommerce.service.interfaces.ProductServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class ProductService implements ProductServiceInterface {

    @Autowired
    UserService userService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    FavoriteProductRepository favoriteProductRepository;
    @Autowired
    ViewedProductRepository viewedProductRepository;
    @Autowired
    CategoryRepository categoryRepository;

    public ProductDto saveProduct(final ProductDto productDto) {
        validateProduct(productDto);

        Product product = ProductMapper.toEntity(productDto);
        product.setUser(userService.getLoggedUser());
        product.getImages().forEach(i -> i.setProduct(product));
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toDto(savedProduct);
    }

    public ProductDto updateProduct(final ProductDto productDto) {
        validateExistingProduct(productDto);

        Product product = ProductMapper.toEntity(productDto);
        product.setUser(userService.getLoggedUser());
        product.getImages().forEach(i -> i.setProduct(product));
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toDto(savedProduct);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteProduct(final Long id) {
        validateOwnership(id, "El producto solo puede ser eliminado por el usuario que lo creo.");
        favoriteProductRepository.deleteByProductId(id);
        viewedProductRepository.deleteByProductId(id);
        productRepository.deleteById(id);
    }

    public List<ProductDto> getAllProducts() {
        final List<Product> products = productRepository.findAll();
        return ProductMapper.toDtoList(products);
    }

    public ProductDto getById(final Long id) {
        final Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el producto."));

        saveViewedProduct(product);

        return ProductMapper.toDto(product);
    }

    public List<ProductDto> getByUserId() {
        final Long userId = userService.getLoggedUser().getId();
        final List<Product> products = productRepository.findByUserId(userId);
        return ProductMapper.toDtoList(products);
    }

    public List<ProductDto> getByCategoryId(final Long categoryId) {
        final List<Product> products = productRepository.findByCategoryId(categoryId);
        return ProductMapper.toDtoList(products);
    }

    public List<ProductDto> getFeaturedProducts() {
        final List<Product> products = productRepository.findByFeatured(true);
        return ProductMapper.toDtoList(products);
    }

    public ProductDto addFavorite(final Long productId) {
        final User user = userService.getLoggedUser();
        final Product product = productRepository.findById(productId)
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

    public void removeFavorite(final Long productId) {
        final Long userId = userService.getLoggedUser().getId();
        final FavoriteProduct favoriteProduct = favoriteProductRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el elemento guardado como favorito."));
        favoriteProductRepository.delete(favoriteProduct);
    }

    public List<ProductDto> getFavorites() {
        final Long userId = userService.getLoggedUser().getId();
        final Set<FavoriteProduct> favoriteProducts = favoriteProductRepository.findByUserId(userId);
        final List<Product> products = favoriteProducts.stream()
                .sorted((p1, p2) -> p2.getAddedDate().compareTo(p1.getAddedDate()))
                .map(FavoriteProduct::getProduct).toList();
        return ProductMapper.toDtoList(products);
    }

    public List<ProductDto> getViewed() {
        final Long userId = userService.getLoggedUser().getId();
        final Set<ViewedProduct> viewedProducts = viewedProductRepository.findByUserId(userId);
        final List<Product> products = viewedProducts.stream()
                .sorted((p1, p2) -> p2.getAddedDate().compareTo(p1.getAddedDate()))
                .map(ViewedProduct::getProduct).limit(10).toList();
        return ProductMapper.toDtoList(products);
    }

    private void saveViewedProduct(final Product product) {
        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            final User user = userService.getLoggedUser();
            if (user != null) {
                ViewedProduct viewedProduct = viewedProductRepository.findByUserIdAndProductId(user.getId(), product.getId()).orElse(null);
                if (viewedProduct == null) {
                    viewedProduct = new ViewedProduct();
                    viewedProduct.setProduct(product);
                    viewedProduct.setUser(user);
                    viewedProduct.setAddedDate(LocalDateTime.now());
                    viewedProductRepository.save(viewedProduct);
                } else {
                    viewedProduct.setAddedDate(LocalDateTime.now());
                }
                viewedProductRepository.save(viewedProduct);
            }
        }
    }

    private void validateProduct(final ProductDto productDto) {
        if(productDto.getName() == null || productDto.getName().isBlank()) {
            throw new ValidationException("Ingrese un nombre para el producto.");
        }

        if(productDto.getDescription() == null || productDto.getDescription().isBlank()) {
            throw new ValidationException("Ingrese una descripción para el producto.");
        }

        if(productDto.getPrice() == null || productDto.getPrice() <= 0) {
            throw new ValidationException("El precio debe ser mayor a 0");
        }

        if(productDto.getQuantity() <= 0) {
            throw new ValidationException("La cantidad debe ser mayor a 0");
        }

        if(productDto.getCategoryId() == null) {
            throw new ValidationException("Ingrese una categoría para el producto.");
        }
        categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new ValidationException("No se encontró la categoría."));

        if(productDto.getImages() == null || productDto.getImages().isEmpty()) {
            throw new ValidationException("Ingrese al menos una imagen para el producto.");
        }
    }

    private void validateExistingProduct(final ProductDto productDto) {
        if(productDto.getId() == null) {
            throw new ValidationException("Ingrese el id del producto.");
        }

        validateOwnership(productDto.getId(), "El producto solo puede ser editado por el usuario que lo creo.");

        validateProduct(productDto);
    }

    private void validateOwnership(final Long productId, final String errorMessage) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el producto."));

        final Long userId = userService.getCurrentUserId();
        if (!Objects.equals(product.getUser().getId(), userId)) {
            throw new NotOwnerException(errorMessage);
        }
    }
}
