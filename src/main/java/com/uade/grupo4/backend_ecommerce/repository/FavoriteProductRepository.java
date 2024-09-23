package com.uade.grupo4.backend_ecommerce.repository;

import com.uade.grupo4.backend_ecommerce.repository.entity.FavoriteProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Repository
public interface FavoriteProductRepository extends JpaRepository<FavoriteProduct, Long> {

    Set<FavoriteProduct> findByUserId(Long userId);
    Optional<FavoriteProduct> findByUserIdAndProductId(Long userId, Long productId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM favorite_product f WHERE f.product_id = :productId", nativeQuery = true)
    void deleteByProductId(@Param("productId") Long productId);
}
