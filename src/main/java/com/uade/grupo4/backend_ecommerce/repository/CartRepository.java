package com.uade.grupo4.backend_ecommerce.repository;

import com.uade.grupo4.backend_ecommerce.repository.entity.Cart;
import com.uade.grupo4.backend_ecommerce.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
     Optional<Cart> findById(Long id);
     Optional<Cart> findByUserAndCheckoutDate(User user, Date checkoutDate);
     Optional<Cart> findByUser(User user);
     Optional<Cart> findByUserAndCheckoutDateIsNotNull(User user);
}
