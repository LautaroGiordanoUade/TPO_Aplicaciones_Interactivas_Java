package com.uade.grupo4.backend_ecommerce.service;

import com.uade.grupo4.backend_ecommerce.repository.CarritoItemRepository;
import com.uade.grupo4.backend_ecommerce.repository.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CarritoItemRepository carritoItemRepository;

    public void agregarProductoAlCarrito(Long carritoID,Long productoId,int cantidad){

    }

    public void eliminarProductoDelCarrito(Long carritoID,Long productoId,int cantidad){

    }

    public void vaciarCarrito (Long carritoId){

    }

    public float checkoutCarrito(Long carritoId){
        return 0;
    }


}
