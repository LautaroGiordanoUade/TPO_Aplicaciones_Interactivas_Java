package com.uade.grupo4.backend_ecommerce.exception;

public class EmptyCartException extends RuntimeException{
    public EmptyCartException(String msg){
        super(msg);
    }
}
