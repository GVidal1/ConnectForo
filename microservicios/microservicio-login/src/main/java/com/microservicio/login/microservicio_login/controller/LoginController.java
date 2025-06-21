package com.microservicio.login.microservicio_login.controller;

import com.microservicio.login.microservicio_login.dto.LoginDTO;
import com.microservicio.login.microservicio_login.dto.RecuperarPasswordDTO;
import com.microservicio.login.microservicio_login.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        Boolean autenticado = loginService.iniciarSesion(loginDTO).block();
        
        if (autenticado != null && autenticado) {
            return ResponseEntity.ok("Autenticación exitosa");
        } else {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }

    @PostMapping("/recuperar-password")
    public ResponseEntity<String> recuperarPassword(@RequestBody RecuperarPasswordDTO recuperarPasswordDTO) {
        String resultado = loginService.recuperarPassword(recuperarPasswordDTO).block();
        
        if (resultado != null && !resultado.startsWith("Error:")) {
            return ResponseEntity.ok(resultado);
        } else {
            return ResponseEntity.badRequest().body(resultado);
        }
    }
} 