package com.example.USER.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.USER.model.UsuarioModel;
import com.example.USER.repository.UsuarioRepository;

@Service
public class UserService {

@Autowired
    private UsuarioRepository usuarioRepository;

public List<UsuarioModel> listarUsuarios() {
    return usuarioRepository.findAll();
}

public UsuarioModel crearUsuario(String user, String pass, String corr) {
    UsuarioModel user1 = new UsuarioModel();
    user1.setUserName(user);
    user1.setPassword(pass);
    user1.setCorreo(corr);
    return usuarioRepository.save(user1);
}








}
