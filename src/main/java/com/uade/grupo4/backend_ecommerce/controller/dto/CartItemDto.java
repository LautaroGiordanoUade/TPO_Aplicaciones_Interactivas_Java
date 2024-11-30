package com.uade.grupo4.backend_ecommerce.controller.dto;


public class CartItemDto {
    private Long id;

    private ProductDto product;
    private String quantity;
    private String price;
    

    public CartItemDto(Long id, ProductDto product, String quantity,String price) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.price=price;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
