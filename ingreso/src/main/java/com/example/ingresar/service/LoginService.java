package com.example.ingresar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ingresar.model.LoginModel;
import com.example.ingresar.repository.LoginRepository;


@Service
public class LoginService {


    @Autowired
    private LoginRepository loginRepository;

    public LoginModel obtenerloginPorId(Long idlogin){
        return loginRepository.findById(idlogin).orElseThrow(() -> new RuntimeException("No se encontro id"));
    }


    



}
