package com.uade.grupo4.backend_ecommerce.repository;


import com.uade.grupo4.backend_ecommerce.repository.entity.CarritoItem;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class CarritoItemRepository {
    private final List<CarritoItem> carritoItemList= new ArrayList<>();

    public CarritoItemRepository(){

    }
    public Optional<CarritoItem> findByIDItem(Long id){
        return carritoItemList.stream()
                .filter(carritoItem -> Objects.equals(carritoItem.getId(), id))
                .findFirst();
    }

}
