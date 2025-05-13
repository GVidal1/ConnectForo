package com.example.USER.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.USER.model.Usuarios;
import com.example.USER.service.UserService;

@RestController
@RequestMapping("api/v1")
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<Usuarios>> listaDeUsuarios() {
        List<Usuarios> usuarios = userService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping("/users")
    public ResponseEntity<?> crearUsuario(@RequestParam String userName, @RequestParam String password, @RequestParam String correo) {
        try {
            UsuarioModel newUser = userService.crearUsuario(userName, password, correo);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
