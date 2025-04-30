package com.microservicio.categorias.microservicio_categorias.controller;

import com.microservicio.categorias.microservicio_categorias.model.Categorias;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.categorias.microservicio_categorias.services.CategoriasService;

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
    public ResponseEntity<Categorias> crearCategorias(@RequestBody Categorias categoria) {
        return ResponseEntity.ok(categoriasService.guardarCategoria(categoria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categorias> buscarCategoriaPorId(@PathVariable Long idCategoria) {
        return ResponseEntity.ok(categoriasService.buscarCategoria(idCategoria));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Categorias> actualizarCategoria(
        @PathVariable Long id,
        @RequestBody Categorias categoria) {
        
        return ResponseEntity.ok(categoriasService.actualizarCategorias(id, categoria));
    }

    

}
