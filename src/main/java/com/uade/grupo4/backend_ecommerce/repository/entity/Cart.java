package com.uade.grupo4.backend_ecommerce.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany
    private List<CartItem> items;
    private float total;
    private Date checkoutDate;




}



