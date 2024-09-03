package com.uade.grupo4.backend_ecommerce.controller.Dtos;

import java.util.List;

public class CartDto {
    private Long id;
    private UserDto user;
    private List<CartItemDto> items;

    public CartDto(Long id, UserDto user, List<CartItemDto> items) {
        this.id = id;
        this.user = user;
        this.items = items;
    }

}
