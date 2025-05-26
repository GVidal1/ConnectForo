package com.microservicio.categorias.microservicio_categorias.controller;

import com.microservicio.categorias.microservicio_categorias.model.Categorias;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<String> eliminarCategoria(@PathVariable Long id) {
        try {
            Categorias categoria = categoriasService.buscarCategoria(id);
            String resultado = categoriasService.borrarCategoria(id);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            // return ResponseEntity.notFound().build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
