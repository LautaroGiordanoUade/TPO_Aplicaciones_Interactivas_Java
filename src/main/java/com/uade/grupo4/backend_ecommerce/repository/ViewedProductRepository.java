package com.uade.grupo4.backend_ecommerce.repository;

import com.uade.grupo4.backend_ecommerce.repository.entity.ViewedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ViewedProductRepository extends JpaRepository<ViewedProduct, Long> {

    Set<ViewedProduct> findByUserId(Long userId);
    Optional<ViewedProduct> findByUserIdAndProductId(Long userId, Long productId);
}
