package com.microservicio.login.microservicio_login.service;

import com.microservicio.login.microservicio_login.clients.UsuarioClient;
import com.microservicio.login.microservicio_login.dto.LoginDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class LoginService {

    @Autowired
    private UsuarioClient usuarioClient;

    public Mono<Boolean> iniciarSesion(LoginDTO loginDTO) {
        return usuarioClient.verificarCredenciales(loginDTO)
                .doOnSuccess(result -> {
                    if (result) {
                        System.out.println("Login exitoso para: " + loginDTO.getCorreo());
                    } else {
                        System.out.println("Login fallido para: " + loginDTO.getCorreo());
                    }
                });
    }
} 