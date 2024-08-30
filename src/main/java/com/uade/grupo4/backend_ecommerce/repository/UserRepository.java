package com.uade.grupo4.backend_ecommerce.repository;
import com.uade.grupo4.backend_ecommerce.repository.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
