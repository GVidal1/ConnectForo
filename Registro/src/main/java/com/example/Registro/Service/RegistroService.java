package com.example.Registro.Service;

import com.example.Registro.Model.RegistroModel;
import com.example.Registro.Repository.RegistroRepository;
import com.example.Registro.clients.UsuarioClient;
import com.example.Registro.dto.UsuarioDTO;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistroService {

    private final RegistroRepository registroRepository;
    private final UsuarioClient usuarioClient;
    private final PasswordEncoder passwordEncoder;

    public RegistroService(
        RegistroRepository registroRepository,
        UsuarioClient usuarioClient,
        PasswordEncoder passwordEncoder
    ) {
        this.registroRepository = registroRepository;
        this.usuarioClient = usuarioClient;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public RegistroModel registrarUsuario(RegistroModel registro) {
        
        String passwordEncriptada = passwordEncoder.encode(registro.getPassword());
        registro.setPassword(passwordEncriptada);

        
        RegistroModel registroGuardado = registroRepository.save(registro);

        UsuarioDTO usuarioDTO = new UsuarioDTO(
            registroGuardado.getNombreUsuario(),
            passwordEncriptada,
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

    public RegistroModel buscarPorNickname(String nickname) {
        return registroRepository.findByNombreUsuario(nickname)
            .orElseThrow(() -> new RuntimeException("Usuario con nickname " + nickname + " no encontrado"));
    }
}

