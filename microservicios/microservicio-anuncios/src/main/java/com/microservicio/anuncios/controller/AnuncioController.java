package com.microservicio.anuncios.controller;

import com.microservicio.anuncios.model.Anuncio;
import com.microservicio.anuncios.service.AnuncioService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anuncios")
public class AnuncioController {
    @Autowired
    private AnuncioService anuncioService;

    @GetMapping
    public ResponseEntity<List<Anuncio>> getAll() {
        List<Anuncio> lista = anuncioService.findAll();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(anuncioService.findById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid Anuncio anuncio) {
        try {
            Anuncio saved = anuncioService.save(anuncio);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error al crear el anuncio: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            anuncioService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/foro/{foroId}")
    public ResponseEntity<List<Anuncio>> getByForo(@PathVariable Long foroId) {
        return ResponseEntity.ok(anuncioService.findByForoId(foroId));
    }

    @GetMapping("/publicacion/{publicacionId}")
    public ResponseEntity<List<Anuncio>> getByPublicacion(@PathVariable Long publicacionId) {
        return ResponseEntity.ok(anuncioService.findByPublicacionId(publicacionId));
    }
} 