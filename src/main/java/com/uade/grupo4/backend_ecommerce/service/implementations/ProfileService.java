package com.uade.grupo4.backend_ecommerce.service.implementations;

import com.uade.grupo4.backend_ecommerce.controller.dto.ProfileDto;
import com.uade.grupo4.backend_ecommerce.controller.dto.UserDto;
import com.uade.grupo4.backend_ecommerce.repository.CartRepository;
import com.uade.grupo4.backend_ecommerce.repository.entity.Cart;
import com.uade.grupo4.backend_ecommerce.repository.entity.User;
import com.uade.grupo4.backend_ecommerce.service.interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserServiceInterface userService; // Usa la interfaz para mayor flexibilidad

    public ProfileDto getProfile() {
        // Obtiene el usuario autenticado usando UserService
        User loggedUser = userService.getLoggedUser();
        if (loggedUser == null) {
            throw new IllegalStateException("El usuario no está autenticado");
        }

        // Filtra los carritos asociados al usuario actual
        List<Cart> carts = cartRepository.findAll().stream()
                .filter(cart -> cart.getUser().getId().equals(loggedUser.getId()) && cart.getCheckoutDate() != null)
                .collect(Collectors.toList());

        return new ProfileDto(loggedUser, carts);
    }

    public ProfileDto updateProfile(ProfileDto profileDto) throws Exception {
        // Obtiene el usuario autenticado usando UserService
        User loggedUser = userService.getLoggedUser();
        if (loggedUser == null) {
            throw new IllegalStateException("El usuario no está autenticado");
        }

        // Crea un UserDto para actualizar los datos del usuario usando UserService
        UserDto userDto = new UserDto(
                loggedUser.getId(),
                profileDto.getFirstName(),
                profileDto.getLastName(),
                profileDto.getEmail(),
                loggedUser.getUsername()
        );
        userService.updateUser(loggedUser.getId(), userDto);

        // Recupera los carritos asociados al usuario después de la actualización
        List<Cart> carts = cartRepository.findAll().stream()
                .filter(cart -> cart.getUser().getId().equals(loggedUser.getId()))
                .collect(Collectors.toList());

        // Devuelve el perfil actualizado
        return new ProfileDto(loggedUser, carts);
    }
}


