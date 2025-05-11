package com.example.USER.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.USER.model.Usuarios;
import com.example.USER.service.UserService;

@RestController
@RequestMapping("/api/usuarios")
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<List<Usuarios>> listaDeUsuarios() {
        List<Usuarios> usuarios = userService.listarUsuarios();

        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<?> buscarUsuarioPorId(@PathVariable Long idUsuario) {
        try {
            Usuarios usuario = userService.obtenerUsuarioPorId(idUsuario);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Se cambio el uso de RequestParam por RequetsBody para mantener la contraseña oculta de la url y mandarla atravez del body
    @PostMapping()
    public ResponseEntity<?> crearUsuario(@RequestBody Usuarios usuarioNuevo) {
        try {
            Usuarios usuarioGuardado = userService.guardarUsuario(usuarioNuevo);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioGuardado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error al crear el usuario: " + e.getMessage());
        }
    }

    // Se ocupa pathvariable para modificar a un usuario en concreto y el obj usuario para pasarlo al metodo de service
    @PutMapping("/{idUsuario}")
    public ResponseEntity<?> actualizarInformacionUsuario(
        @PathVariable Long idUsuario, 
        @RequestBody Usuarios nuevaInfoUsuario) {
        try {
            Usuarios usuarioActualzado = userService.actualizarUsuario(idUsuario, nuevaInfoUsuario);
            return ResponseEntity.ok(usuarioActualzado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error al actualizar el usuario: " + e.getMessage());
        }
    }


    // NO borrar la variable de resultado. Este verifica si existe y si es asi lo borra.
    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<?> borrarUsuarioPorId(@PathVariable Long idUsuario) {
        try {
            String resultado = userService.borrarUsuario(idUsuario);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
