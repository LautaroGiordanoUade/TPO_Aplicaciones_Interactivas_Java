package com.uade.grupo4.backend_ecommerce.controller.dto;

import com.uade.grupo4.backend_ecommerce.repository.model.Cart;
import com.uade.grupo4.backend_ecommerce.repository.model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartDto {
    private Long id;
    private UserDto user;
    private List<CartItemDto> items;
    private String total;

    public CartDto(Long id, UserDto user, List<CartItemDto> items,String total) {
        this.id = id;
        this.user = user;
        this.items = items;
        this.total=total;
    }
    public static CartDto toCart(Cart cart){
        List<CartItemDto> itemDtos=new ArrayList<>();
        for(CartItem item:cart.getItems()){
            itemDtos.add(CartItemDto.toCartItem(item));
        }
        CartDto cartDto=new CartDto(cart.getId(),cart.getUser(),itemDtos,String.valueOf(cart.getTotal())));
        return cartDto;

    }

}
