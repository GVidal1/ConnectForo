package com.example.ingresar.controller;

import com.example.ingresar.dto.LoginDTO;
import com.example.ingresar.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public Mono<ResponseEntity<String>> login(@RequestBody LoginDTO loginDTO) {
        return loginService.iniciarSesion(loginDTO)
                .map(autenticado -> {
                    if (autenticado) {
                        return ResponseEntity.ok("Autenticación exitosa");
                    } else {
                        return ResponseEntity.status(401).body("Credenciales inválidas");
                    }
                });
    }
}