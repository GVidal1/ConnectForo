package com.example.ingresar.service;

import com.example.ingresar.clients.UsuarioClient;
import com.example.ingresar.dto.LoginDTO;

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