package com.uade.grupo4.backend_ecommerce.repository;


import com.uade.grupo4.backend_ecommerce.repository.model.CartItem;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class CartItemRepository {
    private final List<CartItem> cartItemList= new ArrayList<>();

    public CartItemRepository(){

    }
    public Optional<CartItem> findByIDItem(Long id){
        return cartItemList.stream()
                .filter(cartItem -> Objects.equals(cartItem.getId(), id))
                .findFirst();
    }

}
