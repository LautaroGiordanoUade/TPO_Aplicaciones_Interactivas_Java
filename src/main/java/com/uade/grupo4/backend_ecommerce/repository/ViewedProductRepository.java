package com.uade.grupo4.backend_ecommerce.repository;

import com.uade.grupo4.backend_ecommerce.repository.entity.ViewedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ViewedProductRepository extends JpaRepository<ViewedProduct, Long> {

    Set<ViewedProduct> findByUserId(Long userId);
    Optional<ViewedProduct> findByUserIdAndProductId(Long userId, Long productId);

    //@Query("DELETE FROM ViewedProduct f WHERE f.product.id = :productId")
    //void deleteByProductId(@Param("productId") Long productId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM viewed_product f WHERE f.product_id = :productId", nativeQuery = true)
    void deleteByProductId(@Param("productId") Long productId);
}
