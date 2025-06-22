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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Categorías", description = "API para gestión de categorías en ConnectForo")
public class CategoriasController {

    @Autowired
    private CategoriasService categoriasService;

    @Operation(summary = "Listar todas las categorías", description = "Obtiene una lista de todas las categorías disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de categorías obtenida exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Categorias.class))),
        @ApiResponse(responseCode = "204", description = "No hay categorías disponibles")
    })
    @GetMapping()
    public ResponseEntity<List<Categorias>> obtenerCategorias() {
        List<Categorias> categoriasDisponibles = categoriasService.listarCategorias();
        if (categoriasDisponibles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categoriasDisponibles);
    }

    @Operation(summary = "Crear nueva categoría", description = "Crea una nueva categoría en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Categoría creada exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Categorias.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping()
    public ResponseEntity<Categorias> crearCategorias(@RequestBody @Valid Categorias categoria) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriasService.guardarCategoria(categoria));
    }

    @Operation(summary = "Buscar categoría por ID", description = "Obtiene una categoría específica por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría encontrada exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Categorias.class))),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarCategoriaPorId(@PathVariable Long id) {
        try {
            Categorias categoria = categoriasService.buscarCategoria(id);
            return ResponseEntity.ok(categoria);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Actualizar categoría", description = "Actualiza la información de una categoría existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría actualizada exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Categorias.class))),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
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

    @Operation(summary = "Eliminar categoría", description = "Elimina una categoría del sistema por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Categoría eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
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
    @Operation(summary = "Buscar categorías por título", description = "Busca categorías que contengan el título especificado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categorías encontradas exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Categorias.class))),
        @ApiResponse(responseCode = "204", description = "No se encontraron categorías")
    })
    @GetMapping("/buscar/titulo")
    public ResponseEntity<List<Categorias>> buscarPorTitulo(@RequestParam String titulo) {
        List<Categorias> categorias = categoriasService.buscarPorTitulo(titulo);
        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categorias);
    }
    // http://localhost:8084/api/categorias/buscar/descripcion?descripcion=Desarrollo

    @Operation(summary = "Buscar categorías por descripción", description = "Busca categorías que contengan la descripción especificada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categorías encontradas exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Categorias.class))),
        @ApiResponse(responseCode = "204", description = "No se encontraron categorías")
    })
    @GetMapping("/buscar/descripcion")
    public ResponseEntity<List<Categorias>> buscarPorDescripcion(@RequestParam String descripcion) {
        List<Categorias> categorias = categoriasService.buscarPorDescripcion(descripcion);
        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categorias);
    }

    // http://localhost:8084/api/categorias/buscar/longitud-titulo?longitud=5

    @Operation(summary = "Buscar categorías por longitud de título", description = "Busca categorías cuyo título tenga la longitud especificada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categorías encontradas exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Categorias.class))),
        @ApiResponse(responseCode = "204", description = "No se encontraron categorías")
    })
    @GetMapping("/buscar/longitud-titulo")
    public ResponseEntity<List<Categorias>> buscarPorLongitudTitulo(@RequestParam int longitud) {
        List<Categorias> categorias = categoriasService.buscarPorLongitudTitulo(longitud);
        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categorias);
    }

    // http://localhost:8084/api/categorias/buscar/por-fecha?fechaInicio=2024-01-01T00:00:00&fechaFin=2025-12-31T23:59:59

    @Operation(summary = "Buscar categorías por rango de fechas", description = "Busca categorías creadas dentro del rango de fechas especificado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categorías encontradas exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Categorias.class))),
        @ApiResponse(responseCode = "204", description = "No se encontraron categorías")
    })
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
