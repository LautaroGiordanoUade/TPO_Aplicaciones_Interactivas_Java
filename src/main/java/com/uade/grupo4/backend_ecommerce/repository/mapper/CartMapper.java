package com.uade.grupo4.backend_ecommerce.repository.mapper;

import com.uade.grupo4.backend_ecommerce.controller.dto.CartDto;
import com.uade.grupo4.backend_ecommerce.controller.dto.CartItemDto;
import com.uade.grupo4.backend_ecommerce.repository.entity.Cart;
import com.uade.grupo4.backend_ecommerce.repository.entity.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartMapper {

    public static CartDto toDTO(Cart cart){
        List<CartItemDto> itemDtos=new ArrayList<>();
        for(CartItem item:cart.getItems()){
            itemDtos.add(CartItemMapper.toDTO(item));
        }
        CartDto cartDto=new CartDto(cart.getId(),UserMapper.toDTO(cart.getUser()),itemDtos,String.valueOf(cart.getTotal()));
        //Ver de usar el mapper de user para pasar de user a userDTO
        return cartDto;
    }

    public static Cart toEntity(CartDto dto){
        List<CartItem> items=new ArrayList<>();
        for(CartItemDto item:dto.getItems()){
            items.add(CartItemMapper.toEntity(item))
        }
        Cart cart=new Cart(dto.getId(), UserMapper.toEntity(dto.getUser()),items,Float.valueOf(dto.getTotal()));
    }
}
