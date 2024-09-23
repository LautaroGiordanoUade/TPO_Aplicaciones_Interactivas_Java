package com.uade.grupo4.backend_ecommerce.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UserRegistrationDto {
    private String username;
    private String email;
    private String password;
    private LocalDate birthDate;
    private String firstName;
    private String lastName;

    public UserRegistrationDto(String username, String email, String password, LocalDate birthDate, String firstName, String lastName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
