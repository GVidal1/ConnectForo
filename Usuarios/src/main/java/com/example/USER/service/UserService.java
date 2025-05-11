package com.example.USER.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.USER.model.Usuarios;
import com.example.USER.repository.UsuarioRepository;

@Service
public class UserService {

@Autowired
    private UsuarioRepository usuarioRepository;

public List<Usuarios> listarUsuarios() {
    return usuarioRepository.findAll();
}

public Usuarios crearUsuario(String user, String pass, String corr) {
    Usuarios user1 = new Usuarios();
    user1.setNombreUsario(user);
    user1.setPassword(pass);
    user1.setCorreo(corr);
    return usuarioRepository.save(user1);
}

}
