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

    public ProfileDto getProfile() {
        Long userId = getCurrentUserId();
        User user = userRepository.findById(userId).orElseThrow();
        List<Cart> carts = cartRepository.findAll().stream()
                .filter(cart -> cart.getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());
        ProfileDto miPerfilDto = new ProfileDto(user, carts);
        return miPerfilDto;
    }

    public ProfileDto updateProfile(ProfileDto miPerfilDto) {
        Long userId = getCurrentUserId();
        User user = userRepository.findById(userId).orElseThrow();
        user.setFirstName(miPerfilDto.getFirstName());
        user.setLastName(miPerfilDto.getLastName());
        user.setEmail(miPerfilDto.getEmail());
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

        throw new IllegalStateException("El perfil no esta autenticado");
    }
}