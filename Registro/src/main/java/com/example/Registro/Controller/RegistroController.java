package com.example.Registro.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Registro.Model.RegistroModel;
import com.example.Registro.Service.RegistroService;

@RestController
@RequestMapping("/api/v1/registro")
public class RegistroController {

    @Autowired
    private RegistroService registroService;

    
    @PostMapping
    public ResponseEntity<RegistroModel> registrar(@RequestBody RegistroModel registro) {
        RegistroModel nuevoRegistro = registroService.registrarUsuario(registro);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRegistro);
    }

    @GetMapping("/{id}")
public ResponseEntity<?> getRegistroById(@PathVariable Long id) {
    try {
        RegistroModel registro = registroService.buscarRegistro(id);
        return ResponseEntity.ok(registro); 
    } catch (RuntimeException e) {
        return ResponseEntity.notFound().build();
    }
}






}

