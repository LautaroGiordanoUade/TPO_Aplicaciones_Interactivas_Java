package com.uade.grupo4.backend_ecommerce.service.implementations;



import com.uade.grupo4.backend_ecommerce.controller.dto.CartDto;
import com.uade.grupo4.backend_ecommerce.controller.dto.CartItemDto;
import com.uade.grupo4.backend_ecommerce.controller.dto.UserDto;
import com.uade.grupo4.backend_ecommerce.repository.CartItemRepository;
import com.uade.grupo4.backend_ecommerce.repository.CartRepository;
import com.uade.grupo4.backend_ecommerce.repository.ProductRepository;
import com.uade.grupo4.backend_ecommerce.repository.entity.Cart;
import com.uade.grupo4.backend_ecommerce.repository.entity.CartItem;
import com.uade.grupo4.backend_ecommerce.repository.entity.Product;
import com.uade.grupo4.backend_ecommerce.repository.entity.User;
import com.uade.grupo4.backend_ecommerce.repository.mapper.CartMapper;
import com.uade.grupo4.backend_ecommerce.service.interfaces.CartServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CartService implements CartServiceInterface {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;


    public CartDto addProductToCart(Long productId, int quantity) {
        Cart cart = cartRepository.findByUserAndCheckoutDate(new User(1, "", "", "", null, "", ""),
                null).orElse(null); // agregar la busqueda de user

        if (cart == null){
            cart=new Cart();
            cart.setUser(new User(1, "", "", "", null, "", ""));
            cartRepository.save(cart);
        }
        Product product = productRepository.findById(productId).orElseThrow();
        CartItem existingItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId);// VER QUE ROMPE ACA

        if (existingItem != null) {
            if (product.getQuantity() <(existingItem.getQuantity() + quantity)){
                return new CartDto(-1L ,new UserDto(1,"Federico","fed","fe","fe"), null,"0");
            }
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            cartItemRepository.save(existingItem);
            cart.getItems().add(existingItem);
        } else {
            if (product.getQuantity() < quantity ){
                return new CartDto(-2L ,new UserDto(1,"Federico","fed","fe","fe"), null,"0");
            }
            CartItem newItem = new CartItem(cart,product, quantity);
            cartItemRepository.save(newItem);

            cart.getItems().add(newItem);
        }
        cart.setTotal(cart.getTotal() + product.getPrice() * quantity);
        cartRepository.save(cart);
        //return CartMapper.toDTO(cart);
        return new CartDto(cart.getId(),new UserDto(1,"Federico","fed","fe","fe"), (List<CartItemDto>) existingItem,String.valueOf(cart.getTotal()));
    }



    public CartDto removeProductFromCart(Long productId, int quantity) throws Exception {
        Cart cart = cartRepository.findByUserAndCheckoutDate(new User(1, "", "", "", null, "", ""),
                null).orElse(null); // agregar la busqueda de user

        Product product = productRepository.findById(productId).orElseThrow();
        CartItem existingItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId);
        if (existingItem != null) {
            int newQuantity = existingItem.getQuantity() - quantity;
            if (newQuantity < 0) {
                return new CartDto(-1L ,new UserDto(1,"Federico","fed","fe","fe"), null,"0");
            } else if (newQuantity == 0) {
                cartItemRepository.delete(existingItem);
            } else {
                existingItem.setQuantity(newQuantity);
                cartItemRepository.save(existingItem);
            }
        }
        else{
            return new CartDto(-2L ,new UserDto(1,"Federico","fed","fe","fe"), null,"0");
        }
        cart.setTotal(cart.getTotal() - product.getPrice() * quantity);
        cartRepository.save(cart);
        //return CartMapper.toDTO(cart);
        return new CartDto(cart.getId(),new UserDto(1,"Federico","fed","fe","fe"), (List<CartItemDto>) existingItem,"10");
    }


    public boolean emptyCart() {
        Cart cart = cartRepository.findByUserAndCheckoutDate(new User(1, "", "", "", null, "", ""),
                null).orElse(null); // agregar la busqueda de user
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());
        if(cartItems.isEmpty()){
            return false;
        }
        for (CartItem item : cartItems) {
            Long productId = item.getProduct().getId();
            int quantity = item.getQuantity();
            Product product = productRepository.findById(productId).orElseThrow();
            product.setQuantity(product.getQuantity() + quantity);
            productRepository.save(product);
        }
        cartItemRepository.deleteAll(cartItems);
        cart.setItems(new ArrayList<>());
        cartRepository.save(cart);
        return true;
    }

    public float checkoutCart() {
        Cart cart = cartRepository.findByUserAndCheckoutDate(new User(1, "", "", "", null, "", ""),
                null).orElse(null); // agregar la busqueda de user
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());
        for (CartItem item : cartItems) {
            Long productId = item.getProduct().getId();
            int quantity = item.getQuantity();
            Product product = productRepository.findById(productId).orElseThrow();

            if (product.getQuantity() < quantity) {
                return -1;
            }
            product.setQuantity(product.getQuantity() - quantity);
            productRepository.save(product);
            cart.setTotal(cart.getTotal() + 10 * quantity);

        }
        cartItemRepository.deleteAll(cartItems);
        for (CartItem item:cartItems){
            cartItemRepository.deleteById(item.getId());
        }
        cart.setItems(new ArrayList<>());
        cart.setCheckoutDate(new Date());
        cartRepository.save(cart);
        return cart.getTotal();

    }
    //Agregar lo de fecha de Checkout

}
