package com.uade.grupo4.backend_ecommerce.repository;

import com.uade.grupo4.backend_ecommerce.repository.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    //Optional<CartItem> findByIdItem(Long id);
    @Query(nativeQuery = true,value = "SELECT * FROM cart_items where cart_id = :cartId and product_id = :productId")
    CartItem findByCartAndProduct(@Param("cartId") Long cartId, @Param("productId")Long productId);
    //List<CartItem> findByCart(@Param("cartId") Long cartId);
}
