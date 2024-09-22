package com.uade.grupo4.backend_ecommerce.controller.dto;

import lombok.Data;

@Data
public class UserDto {
    private long id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    public UserDto(long id, String username, String email, String firstName, String lastName) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
