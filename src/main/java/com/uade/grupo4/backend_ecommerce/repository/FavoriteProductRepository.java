package com.uade.grupo4.backend_ecommerce.repository;

import com.uade.grupo4.backend_ecommerce.repository.entity.FavoriteProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface FavoriteProductRepository extends JpaRepository<FavoriteProduct, Long> {

    Set<FavoriteProduct> findByUserId(Long userId);
    Optional<FavoriteProduct> findByUserIdAndProductId(Long userId, Long productId);
}
