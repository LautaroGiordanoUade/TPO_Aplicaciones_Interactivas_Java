package com.uade.grupo4.backend_ecommerce.controller.dto;



import com.uade.grupo4.backend_ecommerce.repository.model.CartItem;

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

    public static CartItemDto toCartItem(CartItem item){
        CartItemDto cartItemDto=new CartItemDto(item.getId(),CartDto.toCart(item.getCart()), item.getProduct(),item.getQuantity());
        return cartItemDto;
    }
    // MAPPER
}
