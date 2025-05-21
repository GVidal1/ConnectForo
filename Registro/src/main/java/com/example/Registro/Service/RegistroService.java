package com.example.Registro.Service;

import com.example.Registro.Model.RegistroModel;
import com.example.Registro.Repository.RegistroRepository;
import com.example.Registro.clients.UsuarioClient;
import com.example.Registro.dto.UsuarioDTO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistroService {
    private final RegistroRepository registroRepository;
    private final UsuarioClient usuarioClient;

    public RegistroService(RegistroRepository registroRepository, UsuarioClient usuarioClient) {
        this.registroRepository = registroRepository;
        this.usuarioClient = usuarioClient;
    }

    @Transactional
    public RegistroModel registrarUsuario(RegistroModel registro) {
        
        RegistroModel registroGuardado = registroRepository.save(registro);
        
        
        UsuarioDTO usuarioDTO = new UsuarioDTO(
            registroGuardado.getNombreUsuario(),
            registroGuardado.getPassword(),
            registroGuardado.getCorreo()
        );

        
        boolean exito = usuarioClient.sincronizarUsuario(usuarioDTO).block();
        
        if (!exito) {
            throw new RuntimeException("Error al sincronizar con el servicio de usuarios");
        }

        return registroGuardado;
    }

    public RegistroModel buscarRegistro(Long id) {
        return registroRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Registro no encontrado"));
    }
}

