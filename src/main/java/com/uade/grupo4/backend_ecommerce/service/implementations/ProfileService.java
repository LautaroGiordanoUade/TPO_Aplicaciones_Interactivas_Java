package com.uade.grupo4.backend_ecommerce.service.implementations;

import com.uade.grupo4.backend_ecommerce.controller.dto.ProfileDto;
import com.uade.grupo4.backend_ecommerce.repository.CartRepository;
import com.uade.grupo4.backend_ecommerce.repository.UserRepository;
import com.uade.grupo4.backend_ecommerce.repository.entity.Cart;
import com.uade.grupo4.backend_ecommerce.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    public ProfileDto getProfile() { //representa el perfil del usuario
        Long userId = getCurrentUserId(); //obtengo ID del usuario
        User user = userRepository.findById(userId).orElseThrow(); //
        List<Cart> carts = cartRepository.findAll().stream()
                //filtra los objetos Cart para solo incluir
                //los que tiene el usuario actual y tienen una fecha de checkout no nula.
                .filter(cart -> cart.getUser().getId().equals(user.getId()) && cart.getCheckoutDate() != null)
                .collect(Collectors.toList());
        return new ProfileDto(user, carts);
    }
//para cada cart, obtener el User asociado a ese cart
//y luego obtener el Id de ese User, y verificar si ese Id es igual al Id del usuario actual

    public ProfileDto updateProfile(ProfileDto profileDto) {
        Long userId = getCurrentUserId();
        User user = userRepository.findById(userId).orElseThrow();
        user.setFirstName(profileDto.getFirstName());
        user.setLastName(profileDto.getLastName());
        user.setEmail(profileDto.getEmail());
        userRepository.save(user);

        List<Cart> carts = cartRepository.findAll().stream()
                .filter(cart -> cart.getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());

        return new ProfileDto(user, carts);
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof User currentUser) {
            return currentUser.getId();
        }

        throw new IllegalStateException("El usuario no esta autenticado");
    }
}


