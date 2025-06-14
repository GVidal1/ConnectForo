package com.microservicio.registro.microservicio_registro.service;

import com.microservicio.registro.microservicio_registro.clients.UsuarioClient;
import com.microservicio.registro.microservicio_registro.dto.UsuarioDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegistroService {

    @Autowired
    private UsuarioClient usuarioClient;

    public UsuarioDTO registrarUsuario(UsuarioDTO usuarioDTO) {
        Boolean exito = usuarioClient.sincronizarUsuario(usuarioDTO).block();

        if (Boolean.TRUE.equals(exito)) {
            return usuarioDTO;
        } else {
            throw new RuntimeException("No se pudo registrar el usuario en el microservicio de Usuarios.");
        }
    }
} 