package com.uade.grupo4.backend_ecommerce.controller.Dtos;

import lombok.Data;

@Data
public class UserDto {
    private int id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    public UserDto(int id, String username, String email, String firstName, String lastName) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}