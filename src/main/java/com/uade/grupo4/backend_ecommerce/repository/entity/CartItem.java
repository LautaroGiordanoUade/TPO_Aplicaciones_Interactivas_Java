package com.uade.grupo4.backend_ecommerce.repository.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name ="cart_item")
@Data
@NoArgsConstructor

public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;
    private int quantity;


    public CartItem( Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }


}



