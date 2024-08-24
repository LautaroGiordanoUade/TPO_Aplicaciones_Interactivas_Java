package com.uade.grupo4.backend_ecommerce.repository.entity;

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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<CarritoItem> getItems() {
        return items;
    }

    public void setItems(List<CarritoItem> items) {
        this.items = items;
    }
}



