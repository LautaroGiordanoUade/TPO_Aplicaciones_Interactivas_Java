package com.uade.grupo4.backend_ecommerce.repository.entity;

public class Product {
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private Long categoryId;
    private int quantity;

    public Product(Long id, Long userId, String title, String description, Long categoryId, int quantity) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
