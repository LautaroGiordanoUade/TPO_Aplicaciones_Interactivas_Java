package com.uade.grupo4.backend_ecommerce.controller;



import com.uade.grupo4.backend_ecommerce.controller.dto.CartDto;
import com.uade.grupo4.backend_ecommerce.controller.dto.ProductCartDTO;
import com.uade.grupo4.backend_ecommerce.controller.dto.UserDto;
import com.uade.grupo4.backend_ecommerce.exception.*;
import com.uade.grupo4.backend_ecommerce.repository.entity.User;
import com.uade.grupo4.backend_ecommerce.repository.mapper.UserMapper;
import com.uade.grupo4.backend_ecommerce.service.implementations.CartService;
import com.uade.grupo4.backend_ecommerce.service.implementations.UserService;
import com.uade.grupo4.backend_ecommerce.service.interfaces.CartServiceInterface;
import com.uade.grupo4.backend_ecommerce.service.interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {


    @Autowired
    private CartServiceInterface cartService;
    @Autowired
    private UserService userService;



    @PostMapping("/add")
    public ResponseEntity<Object> addProductToCart(@RequestBody ProductCartDTO productCartDTO){
        try {
            User user=userService.getLoggedUser();
            cartService.addProductToCart(productCartDTO.getProductId(),productCartDTO.getQuantity(), user);
            return ResponseEntity.ok("El producto se ha agregado correctamente");
        }catch(NewProductOutOfStockException | ProductInCartOutOfStockException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/remove")
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
    public ResponseEntity<Object> checkoutCart(){
        try {
            User user=userService.getLoggedUser();
            float total = cartService.checkoutCart(user);
            return ResponseEntity.ok("El carrito tiene un total de "+total);
        }catch (EmptyCartException | ProductOutOfStockException e ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
