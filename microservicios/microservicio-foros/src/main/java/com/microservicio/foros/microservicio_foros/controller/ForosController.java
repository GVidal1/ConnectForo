package com.microservicio.foros.microservicio_foros.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.foros.microservicio_foros.Model.Foros;
import com.microservicio.foros.microservicio_foros.Services.ForosService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/foros")
@Tag(name = "Foros", description = "API para gestión de foros en ConnectForo")
public class ForosController {

    @Autowired
    private ForosService forosService;

    @Operation(summary = "Listar todos los foros", description = "Obtiene una lista de todos los foros disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de foros obtenida exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Foros.class))),
        @ApiResponse(responseCode = "204", description = "No hay foros disponibles")
    })
    @GetMapping()
    public ResponseEntity<List<Foros>> obtenerForos() {
        List<Foros> forosDisponibles = forosService.listarForos();
        if (forosDisponibles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(forosDisponibles);
    }

    @Operation(summary = "Buscar foro por ID", description = "Obtiene un foro específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Foro encontrado exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Foros.class))),
        @ApiResponse(responseCode = "404", description = "Foro no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarForoPorId(@PathVariable Long id) {
        try {
            Foros foro = forosService.buscarForos(id);
            return ResponseEntity.ok(foro);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Crear nuevo foro", description = "Crea un nuevo foro en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Foro creado exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Foros.class))),
        @ApiResponse(responseCode = "400", description = "Error al crear el foro")
    })
    @PostMapping()
    public ResponseEntity<?> crearForo(@RequestBody @Valid Foros foro) {
        try {
            Foros foroGuardado = forosService.guardarForo(foro);
            return ResponseEntity.status(HttpStatus.CREATED).body(foroGuardado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error al crear el foro: " + e.getMessage());
        }
    }

    @Operation(summary = "Actualizar foro", description = "Actualiza la información de un foro existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Foro actualizado exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Foros.class))),
        @ApiResponse(responseCode = "400", description = "Error al actualizar el foro")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarForo(
            @PathVariable Long id,
            @RequestBody @Valid Foros foro) {
        try {
            Foros foroAct = forosService.actualizarForo(id, foro);
            return ResponseEntity.ok(foroAct);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error al actualizar el foro: " + e.getMessage());
        }
    }

    @Operation(summary = "Eliminar foro", description = "Elimina un foro del sistema por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Foro eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Foro no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarForo(@PathVariable Long id) {
        try {
            String resultado = forosService.borrarForo(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    // /api/foros/usuario/{idUsuario}
    @Operation(summary = "Buscar foros por usuario", description = "Obtiene todos los foros creados por un usuario específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Foros encontrados exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Foros.class))),
        @ApiResponse(responseCode = "204", description = "No se encontraron foros para este usuario"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> buscarForosPorUsuario(@PathVariable Long idUsuario) {
        try {
            List<Foros> foros = forosService.buscarForosPorUsuario(idUsuario);
            if (foros.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(foros);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    // /api/foros/buscar/titulo?palabra=React
    @Operation(summary = "Buscar foros por palabra en título", description = "Busca foros cuyo título contenga la palabra especificada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Foros encontrados exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Foros.class))),
        @ApiResponse(responseCode = "204", description = "No se encontraron foros")
    })
    @GetMapping("/buscar/titulo")
    public ResponseEntity<List<Foros>> buscarForosPorPalabraEnTitulo(@RequestParam String palabra) {
        List<Foros> foros = forosService.buscarForosPorPalabraEnTitulo(palabra);
        if (foros.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(foros);
    }
    // /api/foros/buscar/contenido?palabra=Desarrollo
    @Operation(summary = "Buscar foros por palabra en contenido", description = "Busca foros cuyo contenido contenga la palabra especificada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Foros encontrados exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Foros.class))),
        @ApiResponse(responseCode = "204", description = "No se encontraron foros")
    })
    @GetMapping("/buscar/contenido")
    public ResponseEntity<List<Foros>> buscarForosPorPalabraEnContenido(@RequestParam String palabra) {
        List<Foros> foros = forosService.buscarForosPorPalabraEnContenido(palabra);
        if (foros.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(foros);
    }
    // /api/foros/buscar/fechas?fechaInicio=2024-01-01T00:00:00&fechaFin=2024-12-31T23:59:59
    @Operation(summary = "Buscar foros por rango de fechas", description = "Busca foros creados dentro del rango de fechas especificado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Foros encontrados exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Foros.class))),
        @ApiResponse(responseCode = "204", description = "No se encontraron foros")
    })
    @GetMapping("/buscar/fechas")
    public ResponseEntity<List<Foros>> buscarForosPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        List<Foros> foros = forosService.buscarForosPorRangoFechas(fechaInicio, fechaFin);
        if (foros.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(foros);
    }
    // /api/foros/buscar/categoria-usuario?idCategoria=1&idUsuario=1
    @Operation(summary = "Buscar foros por categoría y usuario", description = "Busca foros de una categoría específica creados por un usuario específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Foros encontrados exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Foros.class))),
        @ApiResponse(responseCode = "204", description = "No se encontraron foros"),
        @ApiResponse(responseCode = "404", description = "Categoría o usuario no encontrado")
    })
    @GetMapping("/buscar/categoria-usuario")
    public ResponseEntity<?> buscarForosPorCategoriaYUsuario(
            @RequestParam Long idCategoria,
            @RequestParam Long idUsuario) {
        try {
            List<Foros> foros = forosService.buscarForosPorCategoriaYUsuario(idCategoria, idUsuario);
            if (foros.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(foros);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    // /api/foros/buscar/longitud-titulo?longitud=5
    @Operation(summary = "Buscar foros por longitud de título", description = "Busca foros cuyo título tenga la longitud especificada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Foros encontrados exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Foros.class))),
        @ApiResponse(responseCode = "204", description = "No se encontraron foros")
    })
    @GetMapping("/buscar/longitud-titulo")
    public ResponseEntity<List<Foros>> buscarForosPorLongitudTitulo(@RequestParam int longitud) {
        List<Foros> foros = forosService.buscarForosPorLongitudTitulo(longitud);
        if (foros.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(foros);
    }
    // /api/foros/buscar/despues-de?fecha=2024-01-01T00:00:00
    @Operation(summary = "Buscar foros creados después de una fecha", description = "Busca foros creados después de la fecha especificada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Foros encontrados exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Foros.class))),
        @ApiResponse(responseCode = "204", description = "No se encontraron foros")
    })
    @GetMapping("/buscar/despues-de")
    public ResponseEntity<List<Foros>> buscarForosCreadosDespuesDe(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha) {
        List<Foros> foros = forosService.buscarForosCreadosDespuesDe(fecha);
        if (foros.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(foros);
    }
    // /api/foros/buscar/antes-de?fecha=2024-01-01T00:00:00
    @Operation(summary = "Buscar foros creados antes de una fecha", description = "Busca foros creados antes de la fecha especificada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Foros encontrados exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Foros.class))),
        @ApiResponse(responseCode = "204", description = "No se encontraron foros")
    })
    @GetMapping("/buscar/antes-de")
    public ResponseEntity<List<Foros>> buscarForosCreadosAntesDe(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha) {
        List<Foros> foros = forosService.buscarForosCreadosAntesDe(fecha);
        if (foros.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(foros);
    }
}
