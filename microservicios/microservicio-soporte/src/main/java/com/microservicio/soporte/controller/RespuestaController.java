package com.microservicio.soporte.controller;

import com.microservicio.soporte.model.Respuesta;
import com.microservicio.soporte.service.RespuestaService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/respuestas")
public class RespuestaController {

    @Autowired
    private RespuestaService respuestaService;

    @PostMapping
    public ResponseEntity<Respuesta> createRespuesta(@Valid @RequestBody Respuesta respuesta) {
        Respuesta savedRespuesta = respuestaService.save(respuesta);
        return new ResponseEntity<>(savedRespuesta, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Respuesta> getRespuestaById(@PathVariable Long id) {
        return ResponseEntity.ok(respuestaService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Respuesta>> getAllRespuestas() {
        return ResponseEntity.ok(respuestaService.findAll());
    }

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<List<Respuesta>> getRespuestasByTicketId(@PathVariable Long ticketId) {
        return ResponseEntity.ok(respuestaService.findByTicketId(ticketId));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Respuesta>> getRespuestasByUsuarioId(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(respuestaService.findByUsuarioId(usuarioId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Respuesta> updateRespuesta(@PathVariable Long id, @Valid @RequestBody Respuesta respuesta) {
        return ResponseEntity.ok(respuestaService.update(id, respuesta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRespuesta(@PathVariable Long id) {
        respuestaService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 