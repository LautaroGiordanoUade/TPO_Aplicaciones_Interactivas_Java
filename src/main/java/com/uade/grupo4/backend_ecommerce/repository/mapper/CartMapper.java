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
        return new CartDto(cart.getId(),
                UserMapper.toDto(cart.getUser()),
                itemDtos,
                String.valueOf(cart.getTotal()),
                cart.getCheckoutDate()
        );

    }

    public static Cart toEntity(CartDto dto){
        List<CartItem> items=new ArrayList<>();
        for(CartItemDto item:dto.getItems()){
            items.add(CartItemMapper.toEntity(item));
        }
        return new Cart(dto.getId(),
                UserMapper.toEntity(dto.getUser()),
                items,
                Float.valueOf(dto.getTotal()),
                dto.getChechkoutDate()
                );

    }
}
