package com.uade.grupo4.backend_ecommerce.controllers;


import com.uade.grupo4.backend_ecommerce.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrito")
public class CarritoController {


    @Autowired
    private CarritoService carritoService;

    @PostMapping("/{carritoId}/agregar")
    public ResponseEntity<Void> agregarProductoAlCarrito(@PathVariable Long carritoId, @RequestBody Long productoId, @RequestBody int cantidad){
        carritoService.agregarProductoAlCarrito(carritoId,productoId,cantidad);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{carritoId}/eliminar")
    public ResponseEntity<Void> eliminarProductoDelCarrito(@PathVariable Long carritoId, @RequestBody Long productoId,@RequestBody int cantidad) {
        carritoService.eliminarProductoDelCarrito(carritoId, productoId,cantidad);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{carritoId}/vaciar")
    public ResponseEntity<Void> vaciarCarrito(@PathVariable Long carritoId) {
        carritoService.vaciarCarrito(carritoId);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/{carritoId}/checkout")
    public ResponseEntity<Double> checkoutCarrito(@PathVariable Long carritoId){
        float total = carritoService.checkoutCarrito(carritoId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get")
    public ResponseEntity<String> getcarrito(){

        return ResponseEntity.ok("Hola mundo");
    }

}
