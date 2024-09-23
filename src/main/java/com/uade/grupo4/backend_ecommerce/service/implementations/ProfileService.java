package com.uade.grupo4.backend_ecommerce.service.implementations;

import com.uade.grupo4.backend_ecommerce.controller.dto.ProfileDto;
import com.uade.grupo4.backend_ecommerce.repository.CartRepository;
import com.uade.grupo4.backend_ecommerce.repository.UserRepository;
import com.uade.grupo4.backend_ecommerce.repository.entity.Cart;
import com.uade.grupo4.backend_ecommerce.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Service
public class ProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    public ProfileDto getProfile() {
        Long userId = getCurrentUserId();
        User user = userRepository.findById(userId).orElseThrow();
        List<Cart> carts = cartRepository.findByUser(user);
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
        return new ProfileDto(user, cartRepository.findByUser(user));
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof User currentUser) {
            return currentUser.getId();
        }

        throw new IllegalStateException("El usuario no esta autenticado");
    }
}