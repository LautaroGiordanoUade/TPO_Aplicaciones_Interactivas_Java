package com.uade.grupo4.backend_ecommerce.service.implementations;



import com.uade.grupo4.backend_ecommerce.controller.dto.CartDto;
import com.uade.grupo4.backend_ecommerce.controller.dto.CartItemDto;
import com.uade.grupo4.backend_ecommerce.controller.dto.UserDto;
import com.uade.grupo4.backend_ecommerce.exception.*;
import com.uade.grupo4.backend_ecommerce.repository.CartItemRepository;
import com.uade.grupo4.backend_ecommerce.repository.CartRepository;
import com.uade.grupo4.backend_ecommerce.repository.Enum.RoleEnum;
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
        Cart cart=cartRepository.findByUser(new User(1, "", "", "", null, "", "",RoleEnum.USER)).orElse(null);


        if (cart == null ){
            cart=new Cart();
            cart.setUser(new User(1,"","","",null,"","",RoleEnum.USER));
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
                throw new ProductInCartOutOfStockException("No hay mas cantidad de stock para agregar al producto ingresado"+product.getName());

            }
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            cartItemRepository.save(existingItem);
        } else {
            if (product.getQuantity() < quantity ){
                throw new NewProductOutOfStockException("No hay esa cantidad de stock para agregar al producto ingresado"+product.getName());

            }
            CartItem newItem = new CartItem(product, quantity);
            cartItemRepository.save(newItem);

            cart.getItems().add(newItem);
        }
        float total=cart.getTotal() + (product.getPrice() * quantity);
        cart.setTotal(cart.getTotal() + (product.getPrice() * quantity));
        cartRepository.save(cart);

        return CartMapper.toDTO(cart);

    }



    public CartDto removeProductFromCart(Long productId, int quantity) throws Exception {
        Cart cart = cartRepository.findByUserAndCheckoutDate(new User(1, "", "", "", null, "", "", RoleEnum.USER),
                null).orElse(null); // agregar la busqueda de user
        //validar que existe el cart

        CartItem cartItem= cart.getItems().stream().filter(x -> Objects.equals(x.getProduct().getId(), productId)).findFirst().orElse(null);
        Product product = productRepository.findById(productId).orElseThrow();

        if (cartItem != null) {
            int newQuantity = cartItem.getQuantity() - quantity;
            if (newQuantity < 0) {
                throw new NegativeCartException("No puede quedar la cantidad en negativo");

            } else if (newQuantity == 0) {
                cart.getItems().remove(cartItem);
                cartItemRepository.delete(cartItem);
            } else {
                cartItem.setQuantity(newQuantity);
                cartItemRepository.save(cartItem);
            }
        }
        else{
            throw new ProductRemovalFromCartException("No se puede eliminar un producto que no esta en el carrito");

        }
        cart.setTotal(cart.getTotal() - (product.getPrice() * quantity));
        cartRepository.save(cart);
        return CartMapper.toDTO(cart);

    }


    public boolean emptyCart() {
        Cart cart = cartRepository.findByUserAndCheckoutDate(new User(1, "", "", "", null, "", "", RoleEnum.USER),
                null).orElse(null); // agregar la busqueda de user

        List<CartItem> cartItems = cart.getItems().stream().toList();
        if(cartItems.isEmpty()){
            throw new CartWasEmptyPreviouslyException("El carrito ya estaba vacio");
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

        Cart cart = cartRepository.findByUserAndCheckoutDate(new User(1, "", "", "", null, "", "", RoleEnum.USER),
                null).orElse(null); // agregar la busqueda de user

        List<CartItem> cartItems = cart.getItems().stream().toList();
        if(cartItems.isEmpty()){
            throw new EmptyCartException("No hay items en el carrito");
        }
        for (CartItem item : cartItems) {
            Long productId = item.getProduct().getId();
            int quantity = item.getQuantity();
            Product product = productRepository.findById(productId).orElseThrow();

            if (product.getQuantity() < quantity) {
                throw new ProductOutOfStockException("No hay Stock disponible para el producto"+item.getProduct().getName());
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


}
