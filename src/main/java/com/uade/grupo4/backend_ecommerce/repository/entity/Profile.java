package com.uade.grupo4.backend_ecommerce.repository.entity;

import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.List;

public class Profile {
    private String firstName;
    private String lastName;
    private String email;
    private Long id;



    public Profile(String lastName, String firstName, String email) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}