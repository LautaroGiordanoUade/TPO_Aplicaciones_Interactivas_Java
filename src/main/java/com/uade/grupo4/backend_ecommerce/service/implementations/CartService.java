package com.uade.grupo4.backend_ecommerce.service.implementations;



import com.uade.grupo4.backend_ecommerce.controller.Dtos.CartDto;
import com.uade.grupo4.backend_ecommerce.repository.CartItemRepository;
import com.uade.grupo4.backend_ecommerce.repository.CartRepository;
import com.uade.grupo4.backend_ecommerce.repository.ProductRepository;
import com.uade.grupo4.backend_ecommerce.service.interfaces.CartServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService implements CartServiceInterface {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    public CartDto addProductToCart(Long carritoID, Long productId, int quantity){

    }

    public CartDto removeProductFromCart(Long carritoID,Long productId,int quantity){

    }

    public void emptyCart (Long cartId){

    }

    public float checkoutCart(Long cartId){
        return 0;
    }



}
