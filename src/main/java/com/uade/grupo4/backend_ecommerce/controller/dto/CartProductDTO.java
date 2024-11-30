package com.uade.grupo4.backend_ecommerce.controller.dto;
import lombok.Data;

@Data
public class CartProductDTO {
    private Long productId;
    private String name;
    private int quantity;
    private double price;

    public CartProductDTO(Long productId,String name, int quantity, double price) {
        this.productId = productId;
        this.name=name;
        this.quantity = quantity;
        this.price = price;
    }
}
