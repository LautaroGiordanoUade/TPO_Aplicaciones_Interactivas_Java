package com.uade.grupo4.backend_ecommerce.service.implementations;

import com.uade.grupo4.backend_ecommerce.controller.dto.ProductDto;
import com.uade.grupo4.backend_ecommerce.controller.dto.ProductImageDto;
import com.uade.grupo4.backend_ecommerce.exception.ValidationException;
import com.uade.grupo4.backend_ecommerce.repository.CategoryRepository;
import com.uade.grupo4.backend_ecommerce.repository.ProductRepository;
import com.uade.grupo4.backend_ecommerce.repository.entity.Category;
import com.uade.grupo4.backend_ecommerce.repository.entity.Product;
import com.uade.grupo4.backend_ecommerce.repository.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveProduct_EmptyName_ThrowException() {
        assertThrows(ValidationException.class,
                ()-> {
                    // Arrange
                    ProductDto productDto = new ProductDto(
                            null,
                            null,
                            "Description",
                            1L,
                            1,
                            1L,
                            true,
                            true,
                            Collections.singletonList(new ProductImageDto(1L, 1L, ""))
                    );

                    // Act
                    productService.saveProduct(productDto);
                }
        );
    }

    @Test
    public void testSaveProduct_NoPrice_ThrowException() {
        assertThrows(ValidationException.class,
                ()-> {
                    // Arrange
                    ProductDto productDto = new ProductDto(
                            null,
                            "name",
                            "Description",
                            1L,
                            1,
                            null,
                            true,
                            true,
                            Collections.singletonList(new ProductImageDto(1L, 1L, ""))
                    );

                    // Act
                    productService.saveProduct(productDto);
                }
        );
    }

    @Test
    public void testSaveProduct_NoImages_ThrowException() {
        assertThrows(ValidationException.class,
                ()-> {
                    // Arrange
                    ProductDto productDto = new ProductDto(
                            null,
                            "name",
                            "Description",
                            1L,
                            1,
                            1L,
                            true,
                            true,
                            Collections.emptyList()
                    );

                    // Act
                    productService.saveProduct(productDto);
                }
        );
    }

    @Test
    public void testSaveProduct_Success() {
        // Arrange
        ProductDto productDto = new ProductDto(
                null,
                "Name",
                "Description",
                1L,
                1,
                1L,
                true,
                true,
                Collections.singletonList(new ProductImageDto(1L, 1L, ""))
        );

        Category category = mock(Category.class);
        User user = mock(User.class);
        Product savedProduct = mock(Product.class);

        when(category.getId()).thenReturn(1L);
        when(savedProduct.getCategory()).thenReturn(category);
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);
        when(userService.getLoggedUser()).thenReturn(user);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        // Act
        ProductDto product = productService.saveProduct(productDto);

        // Assert
        assertNotNull(product);
    }
}
