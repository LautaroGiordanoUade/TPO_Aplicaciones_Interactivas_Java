package com.uade.grupo4.backend_ecommerce.controller;

import com.uade.grupo4.backend_ecommerce.repository.entity.Profile;
import com.uade.grupo4.backend_ecommerce.service.implementations.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/perfil")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping
    public ResponseEntity<Profile> getProfile(Principal principal) {
        Profile profile = ProfileService.findByEmail(principal.getName());
        return ResponseEntity.ok(profile);
    }

    //necesita transacciones
}
