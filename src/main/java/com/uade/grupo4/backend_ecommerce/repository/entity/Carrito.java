package com.uade.grupo4.backend_ecommerce.repository.entity;

import java.util.List;

public class Carrito {

    private Long id;

    private Usuario usuario;

    private List<CarritoItem> items;

    public Carrito(Long id, Usuario usuario, List<CarritoItem> items) {
        this.id = id;
        this.usuario = usuario;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CarritoItem> getItems() {
        return items;
    }

    public void setItems(List<CarritoItem> items) {
        this.items = items;
    }
}



