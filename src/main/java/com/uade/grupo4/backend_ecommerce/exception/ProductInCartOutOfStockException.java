package com.uade.grupo4.backend_ecommerce.exception;

public class ProductInCartOutOfStockException extends RuntimeException{
    public ProductInCartOutOfStockException (String msg){
        super(msg);
    }
}
