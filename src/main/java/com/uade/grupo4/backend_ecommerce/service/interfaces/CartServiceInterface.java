package com.uade.grupo4.backend_ecommerce.service.interfaces;

import com.uade.grupo4.backend_ecommerce.controller.dto.CartDto;

public interface CartServiceInterface {
    CartDto addProductToCart(Long id,Long productID,int quantity);
    CartDto removeProductFromCart(Long id,Long productID,int quantity) throws Exception;
    void emptyCart(Long cartID);
    float checkoutCart(Long cartId);
}
