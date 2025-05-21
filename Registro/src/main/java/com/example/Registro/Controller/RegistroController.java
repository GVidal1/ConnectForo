package com.example.Registro.Controller;

import com.example.Registro.Model.RegistroModel;
import com.example.Registro.Service.RegistroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/registro")
public class RegistroController {
    private final RegistroService registroService;

    public RegistroController(RegistroService registroService) {
        this.registroService = registroService;
    }

    @PostMapping
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody RegistroModel registro) {
        try {
            RegistroModel nuevoRegistro = registroService.registrarUsuario(registro);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRegistro);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al registrar: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRegistroById(@PathVariable Long id) {
        try {
            RegistroModel registro = registroService.buscarRegistro(id);
            return ResponseEntity.ok(registro);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
        }
    }
}









