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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/foros")
public class ForosController {

    @Autowired
    private ForosService forosService;

    @GetMapping()
    public ResponseEntity<List<Foros>> obtenerForos() {
        List<Foros> forosDisponibles = forosService.listarForos();
        if (forosDisponibles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(forosDisponibles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarForoPorId(@PathVariable Long id) {
        try {
            Foros foro = forosService.buscarForos(id);
            return ResponseEntity.ok(foro);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<?> crearForo(@RequestBody @Valid Foros foro) {
        try {
            Foros foroGuardado = forosService.guardarForo(foro);
            return ResponseEntity.status(HttpStatus.CREATED).body(foroGuardado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error al crear el foro: " + e.getMessage());
        }
    }

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
    @GetMapping("/buscar/titulo")
    public ResponseEntity<List<Foros>> buscarForosPorPalabraEnTitulo(@RequestParam String palabra) {
        List<Foros> foros = forosService.buscarForosPorPalabraEnTitulo(palabra);
        if (foros.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(foros);
    }
    // /api/foros/buscar/contenido?palabra=Desarrollo
    @GetMapping("/buscar/contenido")
    public ResponseEntity<List<Foros>> buscarForosPorPalabraEnContenido(@RequestParam String palabra) {
        List<Foros> foros = forosService.buscarForosPorPalabraEnContenido(palabra);
        if (foros.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(foros);
    }
    // /api/foros/buscar/fechas?fechaInicio=2024-01-01T00:00:00&fechaFin=2024-12-31T23:59:59
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
    @GetMapping("/buscar/longitud-titulo")
    public ResponseEntity<List<Foros>> buscarForosPorLongitudTitulo(@RequestParam int longitud) {
        List<Foros> foros = forosService.buscarForosPorLongitudTitulo(longitud);
        if (foros.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(foros);
    }
    // /api/foros/buscar/despues-de?fecha=2024-01-01T00:00:00
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
