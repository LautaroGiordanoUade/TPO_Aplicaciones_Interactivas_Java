package com.uade.grupo4.backend_ecommerce.controller;

// ProfileController.java

import com.uade.grupo4.backend_ecommerce.controller.dto.ProfileDto;
import com.uade.grupo4.backend_ecommerce.repository.entity.User;
import com.uade.grupo4.backend_ecommerce.service.implementations.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProfileController {

    private final UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile/{userId}")
    public ProfileDto getProfile(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            ProfileDto profileDTO = new ProfileDto();
            profileDTO.setUserId(user.getId());
            profileDTO.setUsername(user.getname());
            profileDTO.setUserEmail(user.getEmail());
            profileDTO.setPassword(user.getPassword());
            profileDTO.setBirthDate(usergetBirthDate());
            profileDTO.setFirstName(user.getFirstName());
            profileDTO.setLastName(user.getLastName());

            return profileDTO;
        } else {
            throw new RuntimeException("Perfil no encontrado");
        }
    }
}