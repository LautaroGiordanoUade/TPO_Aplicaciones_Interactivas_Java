package com.uade.grupo4.backend_ecommerce.controller;



import com.uade.grupo4.backend_ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {


    @Autowired
    private CartService cartService;

    @PostMapping("/{cartId}/add")
    public ResponseEntity<Void> addProductToCart(@PathVariable Long carritoId, @RequestBody Long productId, @RequestBody int quantity){
        cartService.addProductToCart(carritoId,productId,quantity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cartId}/remove")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable Long carritoId, @RequestBody Long productId,@RequestBody int quantity) {
        cartService.removeProductFromCart(carritoId, productId,quantity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cartId}/empty")
    public ResponseEntity<Void> emptyCart(@PathVariable Long carritoId) {
        cartService.emptyCart(carritoId);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/{cartId}/checkout")
    public ResponseEntity<Double> checkoutCart(@PathVariable Long carritoId){
        float total = cartService.checkoutCart(carritoId);
        return ResponseEntity.ok().build();
    }


}
