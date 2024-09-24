package com.uade.grupo4.backend_ecommerce.repository.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {
    @Test
    public void testGettersAndSetters() {
        // Arrange
        Category category = new Category();

        long id = 1L;
        String name = "Test Category";

        // Act
        category.setId(id);
        category.setName(name);

        // Assert
        assertEquals(id, category.getId());
        assertEquals(name, category.getName());
    }

    @Test
    public void testConstructorWithParameters() {
        // Arrange
        long id = 1L;
        String name = "Test Category";

        // Act
        Category category = new Category(id, name);

        // Assert
        assertEquals(id, category.getId());
        assertEquals(name, category.getName());
    }
}
