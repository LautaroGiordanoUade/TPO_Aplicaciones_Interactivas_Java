package com.uade.grupo4.backend_ecommerce.service.interfaces;

import com.uade.grupo4.backend_ecommerce.controller.dto.CartDto;
import com.uade.grupo4.backend_ecommerce.controller.dto.UserDto;
import com.uade.grupo4.backend_ecommerce.repository.entity.User;

public interface CartServiceInterface {
    CartDto addProductToCart(Long productID, int quantity, User user);
    CartDto removeProductFromCart(Long productID,int quantity,User user);
    boolean emptyCart(User user);
    float checkoutCart(User user);
}
