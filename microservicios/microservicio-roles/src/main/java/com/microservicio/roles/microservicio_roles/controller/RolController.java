package com.microservicio.roles.microservicio_roles.controller;

import com.microservicio.roles.microservicio_roles.clients.UsuarioClient;
import com.microservicio.roles.microservicio_roles.dto.UsuarioDTO;
import com.microservicio.roles.microservicio_roles.model.Rol;
import com.microservicio.roles.microservicio_roles.service.RolService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    @Autowired
    private RolService rolService;

    @Autowired
    private UsuarioClient usuarioClient;

    @GetMapping
    public ResponseEntity<List<Rol>> listarRoles() {
        List<Rol> roles = rolService.listarRoles();
        if (roles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{idRol}")
    public ResponseEntity<?> obtenerRolPorId(@PathVariable Long idRol) {
        try {
            Rol rol = rolService.obtenerRolPorId(idRol);
            return ResponseEntity.ok(rol);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{idRol}/usuarios")
    public ResponseEntity<?> obtenerUsuariosPorRol(@PathVariable Long idRol) {
        try {
            // Primero verificamos que el rol existe
            rolService.obtenerRolPorId(idRol);
            
            // Luego obtenemos los usuarios con ese rol
            List<UsuarioDTO> usuarios = usuarioClient.obtenerUsuariosPorRol(idRol);
            
            if (usuarios == null || usuarios.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            
            return ResponseEntity.ok(usuarios);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("no encontrado")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al obtener usuarios por rol: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> crearRol(@RequestBody @Valid Rol nuevoRol) {
        try {
            Rol rolGuardado = rolService.guardarRol(nuevoRol);
            return ResponseEntity.status(HttpStatus.CREATED).body(rolGuardado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error al crear el rol: " + e.getMessage());
        }
    }

    @PutMapping("/{idRol}")
    public ResponseEntity<?> actualizarRol(@PathVariable Long idRol, @RequestBody @Valid Rol nuevaInfo) {
        try {
            Rol rolActualizado = rolService.actualizarRol(idRol, nuevaInfo);
            return ResponseEntity.ok(rolActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error al actualizar el rol: " + e.getMessage());
        }
    }

    @DeleteMapping("/{idRol}")
    public ResponseEntity<?> borrarRol(@PathVariable Long idRol) {
        try {
            String mensaje = rolService.borrarRol(idRol);
            return ResponseEntity.ok(mensaje);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
} 