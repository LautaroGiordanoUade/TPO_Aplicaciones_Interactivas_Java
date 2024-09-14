package com.uade.grupo4.backend_ecommerce.service.implementations;



import com.uade.grupo4.backend_ecommerce.controller.dto.CartDto;
import com.uade.grupo4.backend_ecommerce.controller.dto.CartItemDto;
import com.uade.grupo4.backend_ecommerce.controller.dto.UserDto;
import com.uade.grupo4.backend_ecommerce.repository.CartItemRepository;
import com.uade.grupo4.backend_ecommerce.repository.CartRepository;
import com.uade.grupo4.backend_ecommerce.repository.ProductRepository;
import com.uade.grupo4.backend_ecommerce.repository.mapper.CartMapper;
import com.uade.grupo4.backend_ecommerce.repository.entity.Cart;
import com.uade.grupo4.backend_ecommerce.repository.entity.CartItem;
import com.uade.grupo4.backend_ecommerce.repository.entity.Product;
import com.uade.grupo4.backend_ecommerce.service.interfaces.CartServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService implements CartServiceInterface {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;


    public CartDto addProductToCart(Long carritoID, Long productId, int quantity) {
        Cart cart = cartRepository.findById(carritoID).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

        CartItem existingItem = cartItemRepository.findByCartAndProduct(cart.getId(), productId);
        if (existingItem != null) {
            if (product.getQuantity() <(existingItem.getQuantity() + quantity)){
                throw  new RuntimeException("No hay suficiente stock de "+product.getName()+"para agregar"+quantity+
                        "como maximo se puede agregar"+(product.getQuantity() - existingItem.getQuantity()));
            }
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            cartItemRepository.save(existingItem);
        } else {
            if (product.getQuantity() < quantity ){
                throw  new RuntimeException("No hay suficiente stock de "+product.getName()+"para agregar"+quantity+
                        "como maximo se puede agregar"+(product.getQuantity()));
            }
            CartItem newItem = new CartItem(carritoID, cart, product, quantity);
            cartItemRepository.save(newItem);
        }
        cart.setTotal(cart.getTotal() + 10 * quantity);
        cartRepository.save(cart);
        return new CartDto(carritoID,new UserDto(1,"Federico","fed","fe","fe"), (List<CartItemDto>) existingItem,"10");
    }

    public CartDto removeProductFromCart(Long carritoID, Long productId, int quantity) throws Exception {
        Cart cart = cartRepository.findById(carritoID).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

        CartItem existingItem = cartItemRepository.findByCartAndProduct(cart.getId(), productId);
        if (existingItem != null) {
            int newQuantity = existingItem.getQuantity() - quantity;
            if (newQuantity < 0) {
                throw new Exception("La cantidad no puede ser negativa");
            } else if (newQuantity == 0) {
                cartItemRepository.delete(existingItem);
            } else {
                existingItem.setQuantity(newQuantity);
                cartItemRepository.save(existingItem);
            }
        }
        else{
            throw new RuntimeException("El producto"+product.getName()+"no existe en el carrito de usted");
        }//preguntar si hay que crear excepciones
        cart.setTotal(cart.getTotal() - 10 * quantity);
        cartRepository.save(cart);
        return new CartDto(carritoID,new UserDto(1,"Federico","fed","fe","fe"), (List<CartItemDto>) existingItem,"10");
    }


    public void emptyCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow();
        /*List<CartItem> cartItems = cartItemRepository.findByCart(cartId);
        for (CartItem item : cartItems) {
            //Long productId = item.getProduct().getId();
            int quantity = item.getQuantity();
            //Product product = productRepository.findById(productId).orElseThrow();
            //product.setQuantity(product.getQuantity() + quantity);
            //productRepository.save(product); // QUE LO PASE A REPOSITORIO
        }
        cartItemRepository.deleteAll(cartItems);
        //cart.setItems(new ArrayList<>());
        cartRepository.save(cart);*/
    }

    public float checkoutCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow();
       /* List<CartItem> cartItems = cartItemRepository.findByCart(cartId);
        for (CartItem item : cartItems) {
            //Long productId = item.getProduct().getId();
            int quantity = item.getQuantity();
            //Product product = productRepository.findById(productId).orElseThrow();
           // if (product.getQuantity() < quantity) {
                //throw new RuntimeException("La cantidad de" + product.getTitle() + "solicita es mayor a nuestro Stock actual");
            //}
            //product.setQuantity(product.getQuantity() - quantity);
            //productRepository.save(product);
            cart.setTotal(cart.getTotal() + 10 * quantity);
        }*/
        //cartItemRepository.deleteAll(cartItems);
        //cart.setItems(new ArrayList<>());
        cartRepository.save(cart);
        return cart.getTotal();

    }

}
