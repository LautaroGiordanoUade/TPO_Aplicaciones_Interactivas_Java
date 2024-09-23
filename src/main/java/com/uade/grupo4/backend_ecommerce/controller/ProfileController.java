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
    private ProfileService profileService;

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ProfileDto> getProfile() {
        ProfileDto profileDto = profileService.getProfile();
        return ResponseEntity.ok(profileDto);
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ProfileDto> updateProfile(@RequestBody ProfileDto profileDto) {
        ProfileDto updatedProfile = profileService.updateProfile(profileDto);
        return ResponseEntity.ok(updatedProfile);
    }
}
