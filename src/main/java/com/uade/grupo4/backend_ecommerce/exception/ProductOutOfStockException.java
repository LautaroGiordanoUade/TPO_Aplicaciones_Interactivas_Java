package com.uade.grupo4.backend_ecommerce.exception;

public class ProductOutOfStockException extends RuntimeException{
    public ProductOutOfStockException (String msg) {
        super(msg);
    }
}
