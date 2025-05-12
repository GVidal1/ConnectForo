package com.example.ingresar.service;



@Service
public class LoginService {

    private LoginRepository loginRepository;

    public LoginModel obtenerloginPorId(long idlogin){
        return loginRepository.findById(idlogin).orElseThrow(() -> new RuntimeException("No se encontro id"));
    }


    



}
