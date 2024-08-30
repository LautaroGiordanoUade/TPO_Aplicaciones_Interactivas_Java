package com.uade.grupo4.backend_ecommerce.service;



import com.uade.grupo4.backend_ecommerce.repository.CartItemRepository;
import com.uade.grupo4.backend_ecommerce.repository.CartRepository;
import com.uade.grupo4.backend_ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    public void addProductToCart(Long carritoID,Long productId,int quantity){

    }

    public void removeProductFromCart(Long carritoID,Long productId,int quantity){

    }

    public void emptyCart (Long cartId){

    }

    public float checkoutCart(Long cartId){
        return 0;
    }


}
