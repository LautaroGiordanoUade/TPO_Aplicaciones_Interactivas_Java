package com.uade.grupo4.backend_ecommerce.repository;

import com.uade.grupo4.backend_ecommerce.repository.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductImageRepository extends JpaRepository<Product, Long> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM product_image f WHERE f.product_id = :productId", nativeQuery = true)
    void deleteByProductId(@Param("productId") Long productId);
}
