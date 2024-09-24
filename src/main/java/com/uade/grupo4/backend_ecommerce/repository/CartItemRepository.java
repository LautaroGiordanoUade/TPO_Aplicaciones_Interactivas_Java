package com.uade.grupo4.backend_ecommerce.repository;

import com.uade.grupo4.backend_ecommerce.repository.entity.Cart;
import com.uade.grupo4.backend_ecommerce.repository.entity.CartItem;
import com.uade.grupo4.backend_ecommerce.repository.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    //Optional<CartItem> findByIdItem(Long id);
    //CartItem findByCartIdAndProductId(long cartId, Long productId);
}
