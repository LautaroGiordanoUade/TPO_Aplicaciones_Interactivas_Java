package com.uade.grupo4.backend_ecommerce.repository.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name ="cartItem")
@Data
@NoArgsConstructor

public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="cart_id")
    private Cart cart;
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;
    private int quantity;


    public CartItem( Cart cart, Product product, int quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }


}



