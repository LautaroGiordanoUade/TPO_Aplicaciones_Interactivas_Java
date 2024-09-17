package com.uade.grupo4.backend_ecommerce.controller;



import com.uade.grupo4.backend_ecommerce.controller.dto.CartDto;
import com.uade.grupo4.backend_ecommerce.controller.dto.ProductCartDTO;
import com.uade.grupo4.backend_ecommerce.service.implementations.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {


    @Autowired
    private CartService cartService;


    @PostMapping("/add")
    public ResponseEntity<Object> addProductToCart(@RequestBody ProductCartDTO productCartDTO){
        CartDto cartAdd= cartService.addProductToCart(productCartDTO.getProductId(),productCartDTO.getQuantity());
        if(cartAdd.getId().equals(-1L)){
            return ResponseEntity.badRequest().body("No hay esa cantidad de stock para agregar al producto ingresado");
        }
        else if (cartAdd.getId().equals(-2L)){
            return ResponseEntity.badRequest().body("No hay esa cantidad de stock para poner del producto ingresado");
        }
        else
            return ResponseEntity.ok("El producto se ha agregado correctamente");
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> deleteProductFromCart(@RequestBody ProductCartDTO productCartDTO) throws Exception {
        CartDto cartDelete=cartService.removeProductFromCart(productCartDTO.getProductId(),productCartDTO.getQuantity());
        if(cartDelete.getId().equals(-1L)){
            return ResponseEntity.badRequest().body("No se puede quedar la cantidad en negativo ");
        }
        else  if(cartDelete.getId().equals(-2L)){
            return ResponseEntity.badRequest().body("No se puede eliminar un producto que no esta en el carrito");
        }
        else
            return ResponseEntity.ok("El producto se ha eliminado correctamente");
    }

    @DeleteMapping("/empty")
    public ResponseEntity<Object> emptyCart() {
        boolean isEmpty=cartService.emptyCart();
        if (isEmpty){
            return ResponseEntity.ok("El carrito se ha vaciado correctamente");
        }
        else{
            return ResponseEntity.badRequest().body("El carrito ya estaba vacio");
        }

    }

    @PostMapping("/checkout")
    public ResponseEntity<Float> checkoutCart(){
        float total = cartService.checkoutCart();
        if (total == -1){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(-1f);
        }
        else{
            return ResponseEntity.ok(total);
        }
    }


    @GetMapping("/HolaCarrito")
    public ResponseEntity<String>TesteandoCarrito (){
        return ResponseEntity.ok("El carrito funciona bien");
    }
}
