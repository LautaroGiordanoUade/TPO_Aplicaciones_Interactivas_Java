package com.uade.grupo4.backend_ecommerce.controller;

// ProfileController.java

import com.uade.grupo4.backend_ecommerce.controller.dto.ProfileDto;
import com.uade.grupo4.backend_ecommerce.controller.dto.UserDto;
import com.uade.grupo4.backend_ecommerce.service.implementations.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ProfileDto getProfile(@PathVariable Long userId) {
        UserDto userDto = userService.getUserById(userId);
        return new ProfileDto(userDto.getId(), userDto.getUsername(), userDto.getEmail(), userDto.getFirstName(), userDto.getLastName());
    }

    @GetMapping("/current")
    public ProfileDto getCurrentProfile() {
        UserDto userDto = userService.getCurrentUser();
        return new ProfileDto(userDto.getId(), userDto.getUsername(), userDto.getEmail(), userDto.getFirstName(), userDto.getLastName());
    }

    @PutMapping("/{userId}")
    public ProfileDto updateProfile(@PathVariable Long userId, @RequestBody ProfileDto profileDto) {
        UserDto userDto = userService.updateUser(userId, profileDto);
        return new ProfileDto(userDto.getId(), userDto.getUsername(), userDto.getEmail(), userDto.getFirstName(), userDto.getLastName());
    }
}
