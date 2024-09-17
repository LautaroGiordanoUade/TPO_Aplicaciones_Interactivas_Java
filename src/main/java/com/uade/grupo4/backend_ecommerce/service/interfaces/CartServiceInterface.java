package com.uade.grupo4.backend_ecommerce.service.interfaces;

import com.uade.grupo4.backend_ecommerce.controller.dto.CartDto;

public interface CartServiceInterface {
    CartDto addProductToCart(Long productID,int quantity);
    CartDto removeProductFromCart(Long productID,int quantity) throws Exception;
    boolean emptyCart();
    float checkoutCart();
}
