package com.uade.grupo4.backend_ecommerce.controller;

import com.uade.grupo4.backend_ecommerce.controller.dto.UserDto;
import com.uade.grupo4.backend_ecommerce.controller.dto.UserRegistrationDto;
import com.uade.grupo4.backend_ecommerce.service.interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserServiceInterface userService;
    // Registro de usuario solo permitido para ADMIN
    @PostMapping("/register")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        UserDto registeredUser = userService.registerUser(userRegistrationDto);
        return ResponseEntity.ok(registeredUser);
    }

    // Modificación de datos del usuario solo permitido para ADMIN
    @PatchMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) throws Exception {
        UserDto updatedUser = userService.updateUser(userId, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    // Obtención de datos de usuario solo permitido para ADMIN
    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDto> getUserById(@PathVariable long userId) throws Exception {
        UserDto user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }
}