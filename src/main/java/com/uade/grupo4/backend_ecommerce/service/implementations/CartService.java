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
import java.util.Objects;

@Service
public class CartService implements CartServiceInterface {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;


    public CartDto addProductToCart(Long productId, int quantity) {
        //Cart cart = cartRepository.findByUserAndCheckoutDate(new User(1, "", "", "", null, "", ""), null).orElse(null); // agregar la busqueda de user
        Cart cart=cartRepository.findByUser(new User(1, "", "", "", null, "", "")).orElse(null);
    //CAMBIAR EL NEW USER POR LA VALIDACION DEL USUARIO, ACA YA FUNCIONA QUE SI ES NUEVO LO CREA Y SINO LO CAMBIA PARA QUE SEA NUEVO y si no es ninguna sigueee
        if (cart == null ){
            cart=new Cart();
            cart.setUser(new User(1, "", "", "", null, "", ""));
            cart.setItems(new ArrayList<CartItem>());
            cartRepository.save(cart);
        }else if (cart.getCheckoutDate() != null) {
            cart.setCheckoutDate(null);
            cart.setTotal(0);
        }
        Product product = productRepository.findById(productId).orElseThrow();
        CartItem existingItem= cart.getItems().stream().filter(x -> Objects.equals(x.getProduct().getId(), productId)).findFirst().orElse(null);
        if (existingItem != null) {
            if (product.getQuantity() <(existingItem.getQuantity() + quantity)){
                return new CartDto(-1L ,new UserDto(1,"Federico","fed","fe","fe"), null,"0");
            }
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            cartItemRepository.save(existingItem);
        } else {
            if (product.getQuantity() < quantity ){

                return new CartDto(-2L ,new UserDto(1,"Federico","fed","fe","fe"), null,"0"); //Ver de poner alguna excepcion
            }
            CartItem newItem = new CartItem(product, quantity);
            cartItemRepository.save(newItem);

            cart.getItems().add(newItem);
        }
        float total=cart.getTotal() + (product.getPrice() * quantity);
        cart.setTotal(cart.getTotal() + (product.getPrice() * quantity));
        cartRepository.save(cart);
        //return CartMapper.toDTO(cart); agregarlo cuando lauti tenga su USERMAPPER
        return new CartDto(cart.getId(),new UserDto(1,"Federico","fed","fe","fe"), new ArrayList<CartItemDto>(),String.valueOf(cart.getTotal()));
    }



    public CartDto removeProductFromCart(Long productId, int quantity) throws Exception {
        Cart cart = cartRepository.findByUserAndCheckoutDate(new User(1, "", "", "", null, "", ""),
                null).orElse(null);// agregar la busqueda de user
        //validar que existe el cart
        CartItem cartItem= cart.getItems().stream().filter(x -> Objects.equals(x.getProduct().getId(), productId)).findFirst().orElse(null);
        Product product = productRepository.findById(productId).orElseThrow();

        if (cartItem != null) {
            int newQuantity = cartItem.getQuantity() - quantity;
            if (newQuantity < 0) {
                return new CartDto(-1L ,new UserDto(1,"Federico","fed","fe","fe"), null,"0");
            } else if (newQuantity == 0) {
                cart.getItems().remove(cartItem);
                cartItemRepository.delete(cartItem);
            } else {
                cartItem.setQuantity(newQuantity);
                cartItemRepository.save(cartItem);
            }
        }
        else{
            return new CartDto(-2L ,new UserDto(1,"Federico","fed","fe","fe"), null,"0");
        }
        cart.setTotal(cart.getTotal() - (product.getPrice() * quantity));
        cartRepository.save(cart);
        //return CartMapper.toDTO(cart); agregarlo cuando lauti tenga su USERMAPPER
        return new CartDto(cart.getId(),new UserDto(1,"Federico","fed","fe","fe"), new ArrayList<CartItemDto>(),"10");
    }


    public boolean emptyCart() {
        Cart cart = cartRepository.findByUserAndCheckoutDate(new User(1, "", "", "", null, "", ""),
                null).orElse(null); // agregar la busqueda de user
        assert cart != null;
        List<CartItem> cartItems = cart.getItems().stream().toList();
        if(cartItems.isEmpty()){
            return false;
        }
        for (CartItem item : cartItems) {
            Long productId = item.getProduct().getId();
            int quantity = item.getQuantity();
            Product product = productRepository.findById(productId).orElseThrow();
            product.setQuantity(product.getQuantity() + quantity);
            productRepository.save(product);
            cart.getItems().remove(item);
        }
        cartItemRepository.deleteAll(cartItems);
        cart.setItems(new ArrayList<>());
        cart.setTotal(0);
        cartRepository.save(cart);
        return true;
    }

    public float checkoutCart() {
        Cart cart = cartRepository.findByUserAndCheckoutDate(new User(1, "", "", "", null, "", ""),
                null).orElse(null); // agregar la busqueda de user
        assert cart != null;
        List<CartItem> cartItems = cart.getItems().stream().toList();
        for (CartItem item : cartItems) {
            Long productId = item.getProduct().getId();
            int quantity = item.getQuantity();
            Product product = productRepository.findById(productId).orElseThrow();

            if (product.getQuantity() < quantity) {
                return -1;
            }
            product.setQuantity(product.getQuantity() - quantity);
            productRepository.save(product);
            cart.getItems().remove(item);

        }
        cartItemRepository.deleteAll(cartItems);
        cart.setItems(new ArrayList<>());
        cart.setCheckoutDate(new Date());
        cartRepository.save(cart);
        return cart.getTotal();

    }
    //Agregar lo de fecha de Checkout

}
