package com.uade.grupo4.backend_ecommerce.repository.mapper;

import com.uade.grupo4.backend_ecommerce.controller.dto.CartItemDto;
import com.uade.grupo4.backend_ecommerce.repository.entity.CartItem;

public class CartItemMapper {


    public static CartItemDto toDTO(CartItem item){
        return new CartItemDto(
                item.getId(),
                ProductMapper.toDto(item.getProduct()),
                String.valueOf(item.getQuantity()),
                String.valueOf(item.getPrice())
        );
    }


    public static CartItem toEntity(CartItemDto dto) {
        return new CartItem(
                ProductMapper.toEntity(dto.getProduct()),
                Integer.parseInt(dto.getQuantity()),
                Double.parseDouble(dto.getPrice())
        );

    }

}
