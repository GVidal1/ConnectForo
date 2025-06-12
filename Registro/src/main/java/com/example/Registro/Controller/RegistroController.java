package com.example.Registro.Controller;

import com.example.Registro.Service.RegistroService;
import com.example.Registro.dto.UsuarioDTO;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/registro")
public class RegistroController {
    @Autowired
    private RegistroService registroService;
    @PostMapping
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody UsuarioDTO usuario) {
        try {
            UsuarioDTO nuevoRegistro = registroService.registrarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRegistro);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al registrar: " + e.getMessage());
        }
    }
}