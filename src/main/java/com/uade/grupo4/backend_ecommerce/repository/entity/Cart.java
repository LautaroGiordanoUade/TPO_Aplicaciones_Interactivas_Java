package com.uade.grupo4.backend_ecommerce.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="cart")
@Data
@NoArgsConstructor

public class Cart {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany
    private List<CartItem> items;
    private float total;
    private Date checkoutDate;

    public Cart(Long id, User user, List<CartItem> items,float total,Date checkoutDate ) {
        this.id = id;
        this.user = user;
        this.items = items;
        this.total=total;
        this.checkoutDate=checkoutDate;
    }


}



