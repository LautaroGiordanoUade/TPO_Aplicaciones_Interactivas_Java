package com.uade.grupo4.backend_ecommerce.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

public class CartDto {
    private Long id;
    private UserDto user;
    private List<CartItemDto> items;
    private String total;
    private Date chechkoutDate;


    public CartDto(Long id, UserDto user, List<CartItemDto> items,String total,Date chechkoutDate) {
        this.id = id;
        this.user = user;
        this.items = items;
        this.total=total;
        this.chechkoutDate=chechkoutDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public List<CartItemDto> getItems() {
        return items;
    }

    public void setItems(List<CartItemDto> items) {
        this.items = items;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Date getChechkoutDate() {
        return chechkoutDate;
    }

    public void setChechkoutDate(Date chechkoutDate) {
        this.chechkoutDate = chechkoutDate;
    }
}
