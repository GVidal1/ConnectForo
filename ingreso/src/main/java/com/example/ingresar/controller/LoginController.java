package com.example.ingresar.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ingresar.model.LoginModel;
import com.example.ingresar.repository.LoginRepository;
import com.example.ingresar.service.LoginService;


@RestController
@RequestMapping("/api/login")
public class LoginController {
    
    @Autowired
    private LoginService loginService;


    @GetMapping("/{idlogin}")
    public ResponseEntity<?> buscarloginporid(@PathVariable Long idLogin) {
        try {
            LoginModel loginModel = loginService.obtenerloginPorId(idLogin);
            return ResponseEntity.ok(loginModel);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
