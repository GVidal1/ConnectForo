package com.example.ingresar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ingresar.clients.RegistroClient;
import com.example.ingresar.model.RegistroModel;

@Service
public class LoginService {

    @Autowired
    private RegistroClient registroClient;

    public RegistroModel obtenerRegistroPorId(Long id) {
        return registroClient.getRegistroPorId(id);
    }
}
