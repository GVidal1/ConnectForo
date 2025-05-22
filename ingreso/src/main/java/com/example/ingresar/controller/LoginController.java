package com.example.ingresar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ingresar.service.LoginService;
import com.example.ingresar.model.RegistroModel;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/{id}")
    public ResponseEntity<RegistroModel> buscarRegistroPorId(@PathVariable Long id) {
        try {
            RegistroModel registro = loginService.obtenerRegistroPorId(id);
            return ResponseEntity.ok(registro);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}



    //@GetMapping("/{idlogin}")
    //public ResponseEntity<?> buscarloginporid(@PathVariable Long id) {
       // try {
            //LoginModel loginModel = loginService.obtenerloginPorId(id);
            //return ResponseEntity.ok(loginModel);
        //} catch (RuntimeException e) {
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        //}
    //}



