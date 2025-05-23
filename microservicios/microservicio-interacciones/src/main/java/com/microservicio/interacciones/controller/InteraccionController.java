package com.microservicio.interacciones.controller;

import com.microservicio.interacciones.model.Interaccion;
import com.microservicio.interacciones.service.InteraccionService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interacciones")
public class InteraccionController {
    @Autowired
    private InteraccionService interaccionService;

    @GetMapping
    public ResponseEntity<List<Interaccion>> getAll() {
        List<Interaccion> lista = interaccionService.findAll();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(interaccionService.findById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid Interaccion interaccion) {
        try {
            Interaccion saved = interaccionService.save(interaccion);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error al crear la interacción: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            interaccionService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/publicacion/{publicacionId}/likes")
    public ResponseEntity<Long> countLikesByPublicacion(@PathVariable Long publicacionId) {
        return ResponseEntity.ok(interaccionService.countLikesByPublicacion(publicacionId));
    }

    @GetMapping("/publicacion/{publicacionId}/dislikes")
    public ResponseEntity<Long> countDislikesByPublicacion(@PathVariable Long publicacionId) {
        return ResponseEntity.ok(interaccionService.countDislikesByPublicacion(publicacionId));
    }

    @GetMapping("/comentario/{comentarioId}/likes")
    public ResponseEntity<Long> countLikesByComentario(@PathVariable Long comentarioId) {
        return ResponseEntity.ok(interaccionService.countLikesByComentario(comentarioId));
    }

    @GetMapping("/comentario/{comentarioId}/dislikes")
    public ResponseEntity<Long> countDislikesByComentario(@PathVariable Long comentarioId) {
        return ResponseEntity.ok(interaccionService.countDislikesByComentario(comentarioId));
    }

    @GetMapping("/publicacion/{publicacionId}")
    public ResponseEntity<List<Interaccion>> getByPublicacion(@PathVariable Long publicacionId) {
        return ResponseEntity.ok(interaccionService.findByPublicacionId(publicacionId));
    }

    @GetMapping("/comentario/{comentarioId}")
    public ResponseEntity<List<Interaccion>> getByComentario(@PathVariable Long comentarioId) {
        return ResponseEntity.ok(interaccionService.findByComentarioId(comentarioId));
    }
} 