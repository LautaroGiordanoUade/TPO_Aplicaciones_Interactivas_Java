package com.uade.grupo4.backend_ecommerce.exception;

public class NegativeCartException extends RuntimeException{
    public NegativeCartException(String msg){
        super(msg);
    }
}
