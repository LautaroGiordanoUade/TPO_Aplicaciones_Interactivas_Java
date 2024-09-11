package com.uade.grupo4.backend_ecommerce.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="cart")
@Data
@NoArgsConstructor

public class Cart {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   // @OneToOne private User user;
    //@OneToMany private List<CartItem> items;
    private float total;

    public Cart(Long id, User user, List<CartItem> items,float total) {
        this.id = id;
        //this.user = user;
        //this.items = items;
        this.total=total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
/*
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }
*/
    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}



