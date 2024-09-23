package com.uade.grupo4.backend_ecommerce.controller.dto;

import com.uade.grupo4.backend_ecommerce.repository.entity.Cart;
import com.uade.grupo4.backend_ecommerce.repository.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private List<CartDto> carts;

    public ProfileDto(User user, List<Cart> carts) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.carts = carts.stream()
                .map(cart -> new CartDto(cart.getId(), new UserDto(cart.getUser().getId(), cart.getUser().getUsername(), cart.getUser().getEmail(), cart.getUser().getFirstName(), cart.getUser().getLastName()), cart.getItems(), cart.getTotal()))
                .collect(Collectors.toList());
    }
}
