package com.uade.grupo4.backend_ecommerce.controller.Dtos;



import com.uade.grupo4.backend_ecommerce.service.model.ProductDto;

public class CartItemDto {
    private Long id;
    private CartDto cart;
    private ProductDto product;
    private int quantity;

    public CartItemDto(Long id, CartDto cart, ProductDto product, int quantity) {
        this.id = id;
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }
}
