package com.example.Registro.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Registro.Model.RegistroModel;
import com.example.Registro.Repository.RegistroRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RegistroService {

    @Autowired
    private RegistroRepository registroRepository;


    public RegistroModel registrarUsuario(RegistroModel registro) {
        return registroRepository.save(registro);
    }

}

