package com.microservicio.categorias.microservicio_categorias.controller;

import com.microservicio.categorias.microservicio_categorias.model.Categorias;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.categorias.microservicio_categorias.services.CategoriasService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/categorias")
public class CategoriasController {

    @Autowired
    private CategoriasService categoriasService;

    @GetMapping()
    public ResponseEntity<List<Categorias>> obtenerCategorias() {
        List<Categorias> categoriasDisponibles = categoriasService.listarCategorias();
        if (categoriasDisponibles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categoriasDisponibles);
    }

    @PostMapping()
    public ResponseEntity<Categorias> crearCategorias(@RequestBody @Valid Categorias categoria) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriasService.guardarCategoria(categoria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarCategoriaPorId(@PathVariable Long id) {
        try {
            Categorias categoria = categoriasService.buscarCategoria(id);
            return ResponseEntity.ok(categoria);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCategoria(
            @PathVariable Long id,
            @RequestBody @Valid Categorias categoria) {
        try {
            Categorias categoriaActualizada = categoriasService.actualizarCategorias(id, categoria);
            return ResponseEntity.ok(categoriaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCategoria(@PathVariable Long id) {
        try {
            Categorias categoria = categoriasService.buscarCategoria(id);
            categoriasService.borrarCategoria(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    // http://localhost:8084/api/categorias/buscar/titulo?titulo=Frontend
    @GetMapping("/buscar/titulo")
    public ResponseEntity<List<Categorias>> buscarPorTitulo(@RequestParam String titulo) {
        List<Categorias> categorias = categoriasService.buscarPorTitulo(titulo);
        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categorias);
    }
    // http://localhost:8084/api/categorias/buscar/descripcion?descripcion=Desarrollo
    @GetMapping("/buscar/descripcion")
    public ResponseEntity<List<Categorias>> buscarPorDescripcion(@RequestParam String descripcion) {
        List<Categorias> categorias = categoriasService.buscarPorDescripcion(descripcion);
        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categorias);
    }
    // http://localhost:8084/api/categorias/buscar/longitud-titulo?longitud=5
    @GetMapping("/buscar/longitud-titulo")
    public ResponseEntity<List<Categorias>> buscarPorLongitudTitulo(@RequestParam int longitud) {
        List<Categorias> categorias = categoriasService.buscarPorLongitudTitulo(longitud);
        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categorias);
    }

    // http://localhost:8084/api/categorias/buscar/por-fecha?fechaInicio=2024-01-01T00:00:00&fechaFin=2025-12-31T23:59:59
    @GetMapping("/buscar/por-fecha")
    public ResponseEntity<List<Categorias>> buscarPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        List<Categorias> categorias = categoriasService.buscarPorRangoFechas(fechaInicio, fechaFin);
        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categorias);
    }
}
