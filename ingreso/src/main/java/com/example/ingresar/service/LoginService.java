package com.example.ingresar.service;

import org.springframework.stereotype.Service;

import com.example.ingresar.clients.RegistroClient;
import com.example.ingresar.dto.UsuarioResponseDTO;

@Service
public class LoginService {
    private final RegistroClient registroClient;

    public LoginService(RegistroClient registroClient) {
        this.registroClient = registroClient;
    }

    public UsuarioResponseDTO obtenerUsuarioPorId(Long id) {
        return registroClient.getUsuarioPorId(id);
    }

    public UsuarioResponseDTO obtenerUsuarioPorNickname(String nickname) {
        return registroClient.getUsuarioPorNickname(nickname);
    }
}
