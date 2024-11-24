package com.uade.grupo4.backend_ecommerce.controller;



import com.uade.grupo4.backend_ecommerce.controller.dto.*;
import com.uade.grupo4.backend_ecommerce.exception.*;
import com.uade.grupo4.backend_ecommerce.repository.entity.Cart;
import com.uade.grupo4.backend_ecommerce.repository.entity.User;
import com.uade.grupo4.backend_ecommerce.repository.mapper.UserMapper;
import com.uade.grupo4.backend_ecommerce.service.implementations.CartService;
import com.uade.grupo4.backend_ecommerce.service.implementations.UserService;
import com.uade.grupo4.backend_ecommerce.service.interfaces.CartServiceInterface;
import com.uade.grupo4.backend_ecommerce.service.interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cart")
public class CartController {


    @Autowired
    private CartServiceInterface cartService;
    @Autowired
    private UserService userService;



    @PostMapping("/add")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Object> addProductToCart(@RequestBody ProductCartDTO productCartDTO){
        try {
            User user=userService.getLoggedUser();
            CartProductDTO productCart =cartService.addProductToCart(productCartDTO.getProductId(),productCartDTO.getQuantity(), user);
            return ResponseEntity.ok(productCart);
        }catch(NewProductOutOfStockException | ProductInCartOutOfStockException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/remove")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> deleteProductFromCart(@RequestBody ProductCartDTO productCartDTO) throws Exception {
        try{
            User user=userService.getLoggedUser();
            cartService.removeProductFromCart(productCartDTO.getProductId(),productCartDTO.getQuantity(), user);
            return ResponseEntity.ok("El producto se ha eliminado correctamente");
        }catch (NegativeCartException | ProductRemovalFromCartException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @DeleteMapping("/empty")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Object> emptyCart() {
        try {
            User user=userService.getLoggedUser();
            cartService.emptyCart(user);
            return ResponseEntity.ok("El carrito se ha vaciado correctamente");
        }catch (CartWasEmptyPreviouslyException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/checkout")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Object> checkoutCart(){
        try {
            User user=userService.getLoggedUser();
            float total = cartService.checkoutCart(user);
            return ResponseEntity.ok("El carrito tiene un total de "+total);
        }catch (EmptyCartException | ProductOutOfStockException e ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Object> getProductsCart(@RequestParam(required = false) String search) {
        User user=userService.getLoggedUser();
        final CartDto cart = cartService.getCartsByUser(user);
        return ResponseEntity.ok(cart);
    }




}
