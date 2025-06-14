package com.microservicio.anuncios.controller;

import com.microservicio.anuncios.model.Anuncio;
import com.microservicio.anuncios.service.AnuncioService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    // /api/anuncios/moderador/{moderadorId}
    @GetMapping("/moderador/{moderadorId}")
    public ResponseEntity<List<Anuncio>> getByModerador(@PathVariable Long moderadorId) {
        List<Anuncio> anuncios = anuncioService.findByModeradorId(moderadorId);
        if (anuncios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(anuncios);
    }
    // /api/anuncios/buscar/mensaje?palabra=React
    @GetMapping("/buscar/mensaje")
    public ResponseEntity<List<Anuncio>> buscarPorPalabraEnMensaje(@RequestParam String palabra) {
        List<Anuncio> anuncios = anuncioService.findByPalabraEnMensaje(palabra);
        if (anuncios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(anuncios);
    }
    // /api/anuncios/buscar/fechas?fechaInicio=2024-01-01T00:00:00&fechaFin=2024-12-31T23:59:59
    @GetMapping("/buscar/fechas")
    public ResponseEntity<?> buscarPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        try {
            List<Anuncio> anuncios = anuncioService.findByRangoFechas(fechaInicio, fechaFin);
            if (anuncios.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(anuncios);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // /api/anuncios/buscar/despues-de?fecha=2024-01-01T00:00:00
    @GetMapping("/buscar/despues-de")
    public ResponseEntity<List<Anuncio>> buscarCreadosDespuesDe(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha) {
        List<Anuncio> anuncios = anuncioService.findCreadosDespuesDe(fecha);
        if (anuncios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(anuncios);
    }
    // /api/anuncios/buscar/antes-de?fecha=2024-01-01T00:00:00
    @GetMapping("/buscar/antes-de")
    public ResponseEntity<List<Anuncio>> buscarCreadosAntesDe(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha) {
        List<Anuncio> anuncios = anuncioService.findCreadosAntesDe(fecha);
        if (anuncios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(anuncios);
    }
    // /api/anuncios/buscar/longitud-mensaje?longitud=5
    @GetMapping("/buscar/longitud-mensaje")
    public ResponseEntity<List<Anuncio>> buscarPorLongitudMensaje(@RequestParam int longitud) {
        List<Anuncio> anuncios = anuncioService.findByLongitudMensaje(longitud);
        if (anuncios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(anuncios);
    }   
    // /api/anuncios/buscar/moderador-foro?moderadorId=1&foroId=1
    @GetMapping("/buscar/moderador-foro")
    public ResponseEntity<List<Anuncio>> buscarPorModeradorYForo(
            @RequestParam Long moderadorId,
            @RequestParam Long foroId) {
        List<Anuncio> anuncios = anuncioService.findByModeradorYForo(moderadorId, foroId);
        if (anuncios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(anuncios);
    }
    // /api/anuncios/buscar/moderador-publicacion?moderadorId=1&publicacionId=1
    @GetMapping("/buscar/moderador-publicacion")
    public ResponseEntity<List<Anuncio>> buscarPorModeradorYPublicacion(
            @RequestParam Long moderadorId,
            @RequestParam Long publicacionId) {
        List<Anuncio> anuncios = anuncioService.findByModeradorYPublicacion(moderadorId, publicacionId);
        if (anuncios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(anuncios);
    }
    // /api/anuncios/contar/moderador/{moderadorId}
    @GetMapping("/contar/moderador/{moderadorId}")
    public ResponseEntity<Long> contarPorModerador(@PathVariable Long moderadorId) {
        return ResponseEntity.ok(anuncioService.contarPorModerador(moderadorId));
    }
    // /api/anuncios/contar/foro/{foroId}
    @GetMapping("/contar/foro/{foroId}")
    public ResponseEntity<Long> contarPorForo(@PathVariable Long foroId) {
        return ResponseEntity.ok(anuncioService.contarPorForo(foroId));
    }
    // /api/anuncios/contar/publicacion/{publicacionId}
    @GetMapping("/contar/publicacion/{publicacionId}")
    public ResponseEntity<Long> contarPorPublicacion(@PathVariable Long publicacionId) {
        return ResponseEntity.ok(anuncioService.contarPorPublicacion(publicacionId));
    }
} 