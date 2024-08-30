package com.uade.grupo4.backend_ecommerce.repository;


import com.uade.grupo4.backend_ecommerce.repository.model.Cart;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class CartRepository {
    private  final List<Cart> cartList= new ArrayList<>();


    public CartRepository(){

    }

    public Optional<Cart> findByID(Long id){
        return  cartList.stream()
                .filter(cart -> Objects.equals(cart.getId(), id))
                .findFirst();
    }



}
