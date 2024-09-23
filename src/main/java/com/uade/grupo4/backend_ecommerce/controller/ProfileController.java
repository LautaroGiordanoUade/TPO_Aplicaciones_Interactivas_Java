package com.uade.grupo4.backend_ecommerce.controller;

import com.uade.grupo4.backend_ecommerce.controller.dto.ProfileDto;
import com.uade.grupo4.backend_ecommerce.service.implementations.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/profile")
public class ProfileController {

    @Autowired
    private ProfileService miPerfilService;

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ProfileDto> getProfile() {
        ProfileDto miPerfilDto = miPerfilService.getProfile();
        return ResponseEntity.ok(miPerfilDto);
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ProfileDto> updateProfile(@RequestBody ProfileDto miPerfilDto) {
        ProfileDto updatedMiPerfilDto = miPerfilService.updateProfile(miPerfilDto);
        return ResponseEntity.ok(updatedMiPerfilDto);
    }
}
