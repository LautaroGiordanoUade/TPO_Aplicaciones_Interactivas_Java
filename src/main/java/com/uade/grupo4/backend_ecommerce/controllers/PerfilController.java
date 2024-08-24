package com.uade.grupo4.backend_ecommerce.controllers;

import com.uade.grupo4.backend_ecommerce.repository.entities.Usuario;
import com.uade.grupo4.backend_ecommerce.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<Usuario> getPerfil(Principal principal) {
        Usuario usuario = usuarioService.findByEmail(principal.getName());
        return ResponseEntity.ok(usuario);
    }

    //necesita transacciones
}
