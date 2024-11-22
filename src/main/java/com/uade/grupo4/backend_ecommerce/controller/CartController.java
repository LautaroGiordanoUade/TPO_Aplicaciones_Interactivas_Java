package com.uade.grupo4.backend_ecommerce.controller;



import com.uade.grupo4.backend_ecommerce.controller.dto.*;
import com.uade.grupo4.backend_ecommerce.exception.*;
import com.uade.grupo4.backend_ecommerce.repository.entity.CartItem;
import com.uade.grupo4.backend_ecommerce.repository.entity.User;
import com.uade.grupo4.backend_ecommerce.repository.mapper.CartItemMapper;
import com.uade.grupo4.backend_ecommerce.repository.mapper.UserMapper;
import com.uade.grupo4.backend_ecommerce.service.implementations.CartService;
import com.uade.grupo4.backend_ecommerce.service.implementations.UserService;
import com.uade.grupo4.backend_ecommerce.service.interfaces.CartServiceInterface;
import com.uade.grupo4.backend_ecommerce.service.interfaces.ProductServiceInterface;
import com.uade.grupo4.backend_ecommerce.service.interfaces.UserServiceInterface;
import jakarta.persistence.criteria.CriteriaBuilder;
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
    ProductServiceInterface productService;
    @Autowired
    private UserService userService;



    @PostMapping("/add")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Object> addProductToCart(@RequestBody CartItemDto cartItemDto){
        try {
            User user=userService.getLoggedUser();
            int quantity=cartService.getProductQuantityInCart(user,cartItemDto.getId());
            cartService.addProductToCart(cartItemDto.getId(),quantity, user);
            CartItem cartItemDtoResponsive=cartService.getCartItemById(cartItemDto.getId(),user);
            return ResponseEntity.ok(CartItemMapper.toDTO(cartItemDtoResponsive));
        }catch(NewProductOutOfStockException | ProductInCartOutOfStockException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> deleteProductFromCart(@RequestBody Long id) throws Exception {
        try{
            User user=userService.getLoggedUser();
            int quantity=cartService.getProductQuantityInCart(user,id);
            cartService.removeProductFromCart(id,quantity, user);
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
    public ResponseEntity<Object> getCartUser() throws Exception {
        UserDto user=userService.getUserById(1L);
        System.out.println("Aca estoy");
        final CartDto cart = cartService.getCartUser(UserMapper.toEntity(user));
        return ResponseEntity.ok(cart);
    }
    //PARA BUSCAR EL CARRITO DE ESE USUARIO




    // EN EL CARTITEM YA PUEDO MOSTRAR EL PRODUCTO PORQUE TENGO LA IMAGEN EN EL PRODUCTIID

//REVISAR AMBOS
    @GetMapping("/quantity")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Integer> getProductQuantityInCart(@RequestParam CartItemDto cartItemDto) throws Exception {
        User user=userService.getLoggedUser();

        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        int quantity = cartService.getProductQuantityInCart(user, cartItemDto.getId());
        return ResponseEntity.ok(quantity);
    }



    @PutMapping("/update")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<CartDto> updateProductInCart(@RequestParam  CartItemDto cartItemDto) {
        try {
            User user=userService.getLoggedUser();
            CartDto updatedCart = cartService.updateProductInCart(cartItemDto.getId(), Integer.valueOf(cartItemDto.getQuantity()), user);
            return ResponseEntity.ok(updatedCart);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    //deberia hacer uno para actualizar el carrito
    //uno para obtener la cantidad que tiene ese producto en el carrito
    //uno para obtener las purchases pasadas
    //uno para guardar las purchasesHistorys


}
