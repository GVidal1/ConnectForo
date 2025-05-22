package com.example.Registro.Controller;

import com.example.Registro.Model.RegistroModel;
import com.example.Registro.Service.RegistroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/registro")
public class RegistroController {

    @Autowired
    private RegistroService registroService;

    @PostMapping
    public ResponseEntity<RegistroModel> registrar(@Valid @RequestBody RegistroModel registro) {
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        });
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }
}









