package com.uade.grupo4.backend_ecommerce.exception;

public class NotOwnerException extends RuntimeException {
    public NotOwnerException(String msg) {
        super(msg);
    }
}
