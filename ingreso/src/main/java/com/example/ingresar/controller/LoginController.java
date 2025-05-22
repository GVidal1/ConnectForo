package com.example.ingresar.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ingresar.dto.UsuarioResponseDTO;
import com.example.ingresar.service.LoginService;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuarioPorId(@PathVariable Long id) {
        return ResponseEntity.ok(loginService.obtenerUsuarioPorId(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuarioPorNickname(@RequestParam String nickname) {
        return ResponseEntity.ok(loginService.obtenerUsuarioPorNickname(nickname));
    }
}


    