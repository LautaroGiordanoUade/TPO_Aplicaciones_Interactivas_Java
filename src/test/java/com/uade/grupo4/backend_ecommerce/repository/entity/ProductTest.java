package com.uade.grupo4.backend_ecommerce.repository.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    @Test
    public void testGettersAndSetters() {
        // Arrange
        Product product = new Product();
        long id = 1L;
        User user = new User();
        String name = "Test Product";
        String description = "This is a test product";
        Category category = new Category();
        int quantity = 10;
        long price = 10000L;
        boolean featured = true;

        // Act
        product.setId(id);
        product.setUser(user);
        product.setName(name);
        product.setDescription(description);
        product.setCategory(category);
        product.setQuantity(quantity);
        product.setPrice(price);
        product.setFeatured(featured);

        // Assert
        assertEquals(id, product.getId());
        assertEquals(user, product.getUser());
        assertEquals(name, product.getName());
        assertEquals(description, product.getDescription());
        assertEquals(category, product.getCategory());
        assertEquals(quantity, product.getQuantity());
        assertEquals(price, product.getPrice());
        assertEquals(featured, product.isFeatured());
    }

    @Test
    public void testCollectionsInitialization() {
        // Arrange

        // Act
        Product product = new Product();

        // Assert
        assertNotNull(product.getFavorites());
        assertNotNull(product.getViewed());
        assertNotNull(product.getImages());
    }

    @Test
    public void testAddingAndRemovingFromCollections() {
        // Arrange
        Product product = new Product();
        FavoriteProduct favoriteProduct = new FavoriteProduct();
        ViewedProduct viewedProduct = new ViewedProduct();
        ProductImage image = new ProductImage();

        // Act
        product.getFavorites().add(favoriteProduct);
        product.getViewed().add(viewedProduct);
        product.getImages().add(image);

        product.getFavorites().remove(favoriteProduct);
        product.getViewed().remove(viewedProduct);
        product.getImages().remove(image);

        // Assert
        assertTrue(product.getFavorites().isEmpty());
        assertTrue(product.getViewed().isEmpty());
        assertTrue(product.getImages().isEmpty());
    }
}
