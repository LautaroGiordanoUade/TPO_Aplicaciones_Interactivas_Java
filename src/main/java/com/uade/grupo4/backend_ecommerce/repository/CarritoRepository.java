package com.uade.grupo4.backend_ecommerce.repository;

import com.uade.grupo4.backend_ecommerce.repository.entity.Carrito;
import com.uade.grupo4.backend_ecommerce.repository.entity.CarritoItem;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class CarritoRepository {
    private  final List<Carrito> carritoList= new ArrayList<>();


    public CarritoRepository(){

    }

    public Optional<Carrito> findByID(Long id){
        return  carritoList.stream()
                .filter(carrito -> Objects.equals(carrito.getId(), id))
                .findFirst();
    }



}
