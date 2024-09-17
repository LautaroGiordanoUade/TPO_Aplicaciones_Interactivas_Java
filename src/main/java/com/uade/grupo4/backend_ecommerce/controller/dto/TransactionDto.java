package com.uade.grupo4.backend_ecommerce.controller.dto;

import java.util.Date;

public class TransactionDto {
    private Long id;
    private CartDto cart;
    private Date transactionDate;

    public CartDto getCart() {
        return cart;
    }

    public void setCart(CartDto cart) {
        this.cart = cart;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}
