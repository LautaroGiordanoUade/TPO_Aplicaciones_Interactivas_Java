package com.uade.grupo4.backend_ecommerce.controller;



import com.uade.grupo4.backend_ecommerce.service.implementations.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {


    @Autowired
    private CartService cartService;


    @PostMapping("/{cartId}/add")
    public ResponseEntity<Object> addProductToCart(@PathVariable Long carritoId, @RequestBody Long productId, @RequestBody int quantity){
        cartService.addProductToCart(carritoId,productId,quantity);
        return ResponseEntity.ok("El producto se ha agregado correctamente");
    }

    @DeleteMapping("/{cartId}/remove")
    public ResponseEntity<Object> removeProductFromCart(@PathVariable Long carritoId, @RequestBody Long productId,@RequestBody int quantity) throws Exception {
        cartService.removeProductFromCart(carritoId, productId,quantity);
        return ResponseEntity.ok("El producto se ha eliminado correctamente");
    }

    @DeleteMapping("/{cartId}/empty")
    public ResponseEntity<Object> emptyCart(@PathVariable Long carritoId) {
        boolean isEmpty=cartService.emptyCart(carritoId);
        if (isEmpty){
            return ResponseEntity.ok("El carrito se ha vaciado correctamente");
        }
        else{
            return ResponseEntity.badRequest().body("El carrito ya estaba vacio");
        }

    }


    @PostMapping("/{cartId}/checkout")
    public ResponseEntity<Float> checkoutCart(@PathVariable Long carritoId){
        float total = cartService.checkoutCart(carritoId);
        return ResponseEntity.ok(total);
    }


    @GetMapping("/HolaCarrito")
    public ResponseEntity<String>TesteandoCarrito (){
        return ResponseEntity.ok("El que se mueve es gay");
    }
}
