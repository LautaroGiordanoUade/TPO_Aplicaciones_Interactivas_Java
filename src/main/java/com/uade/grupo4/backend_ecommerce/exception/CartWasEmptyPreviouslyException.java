package com.uade.grupo4.backend_ecommerce.exception;

public class CartWasEmptyPreviouslyException extends RuntimeException{

    public CartWasEmptyPreviouslyException (String msg){
        super(msg);
    }

}
