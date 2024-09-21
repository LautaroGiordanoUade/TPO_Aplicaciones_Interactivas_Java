package com.uade.grupo4.backend_ecommerce.exception;

public class NewProductOutOfStockException extends RuntimeException{
    public NewProductOutOfStockException(String msg){
        super(msg);
    }
}
