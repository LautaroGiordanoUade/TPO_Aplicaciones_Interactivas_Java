package com.uade.grupo4.backend_ecommerce.exception;

public class ProductRemovalFromCartException extends RuntimeException{
    public ProductRemovalFromCartException (String msg){
        super(msg);
    }
}
