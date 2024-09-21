package com.uade.grupo4.backend_ecommerce.controller.auth;

import com.uade.grupo4.backend_ecommerce.repository.Enum.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private RoleEnum role;
}
