package com.uade.grupo4.backend_ecommerce.controller;

import com.uade.grupo4.backend_ecommerce.service.interfaces.UserServiceInterface;
import com.uade.grupo4.backend_ecommerce.controller.dto.UserRegistrationDto;
import com.uade.grupo4.backend_ecommerce.controller.dto.UserLoginDto;
import com.uade.grupo4.backend_ecommerce.controller.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserServiceInterface userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        UserDto registeredUser = userService.registerUser(userRegistrationDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> loginUser(@RequestBody UserLoginDto userLoginDto) {
        UserDto loggedInUser = userService.loginUser(userLoginDto);
        return ResponseEntity.ok(loggedInUser);
    }

    //Endpoint to test endpoint to be deleted
    @GetMapping("/login")
    public ResponseEntity<String> loginTestUser() {
        return ResponseEntity.ok("Usuario Loggeado");
    }
}