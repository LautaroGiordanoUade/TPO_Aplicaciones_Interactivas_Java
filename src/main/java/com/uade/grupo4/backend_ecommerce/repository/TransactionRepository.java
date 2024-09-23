package com.uade.grupo4.backend_ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.profile.id = :profileId")
    List<Transaction> findByProfileId(@Param("profileId") Long profileId);
}
