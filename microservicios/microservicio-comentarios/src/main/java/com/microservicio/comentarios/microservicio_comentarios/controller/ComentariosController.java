package com.microservicio.comentarios.microservicio_comentarios.controller;

import java.util.List;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.format.annotation.DateTimeFormat;

import com.microservicio.comentarios.microservicio_comentarios.model.Comentarios;
import com.microservicio.comentarios.microservicio_comentarios.services.ComentariosService;

@RestController
@RequestMapping("/api/comentarios")
public class ComentariosController {

  @Autowired
  private ComentariosService comentariosService;

  @GetMapping()
  public ResponseEntity<List<Comentarios>> listaDeComentarios() {
    List<Comentarios> comentariosExistentes = comentariosService.listarComentarios();
    if (comentariosExistentes.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(comentariosExistentes);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> buscarComentarioPorId(@PathVariable Long id) {
    try {
      Comentarios comentarioAct = comentariosService.buscarComentarioPorId(id);
      return ResponseEntity.ok(comentarioAct);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> eliminarComentarioPorId(@PathVariable Long id) {
    try {
      String comentarioAct = comentariosService.borrarComentarioPorId(id);
      return ResponseEntity.noContent().build();
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }

  @PostMapping()
  public ResponseEntity<?> crearNuevoComentario(@RequestBody Comentarios comentarioNuevo) {
    try {
      Comentarios comentario = comentariosService.guardarComentario(comentarioNuevo);
      return ResponseEntity.status(HttpStatus.CREATED).body(comentario);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No es posible crear el comentario: " + e.getMessage());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> actualizarComentarioPorId(
    @PathVariable Long id,
    @RequestBody Comentarios comentarioActualizado
    ) {
      try {
        Comentarios comentario = comentariosService.actualizarComentario(id, comentarioActualizado);
        return ResponseEntity.ok(comentario);
      } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body("Error al actualizar el comentario: " + e.getMessage());
      }
    }

  // /api/comentarios/buscar/contenido?palabra=Desarrollo
  @GetMapping("/buscar/contenido")
  public ResponseEntity<List<Comentarios>> buscarPorPalabraEnContenido(@RequestParam String palabra) {
    List<Comentarios> comentarios = comentariosService.buscarPorPalabraEnContenido(palabra);
    if (comentarios.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(comentarios);
  }
  // /api/comentarios/usuario/{idUsuario}
  @GetMapping("/usuario/{idUsuario}")
  public ResponseEntity<?> buscarPorUsuario(@PathVariable Long idUsuario) {
    try {
      List<Comentarios> comentarios = comentariosService.buscarPorUsuario(idUsuario);
      if (comentarios.isEmpty()) {
        return ResponseEntity.noContent().build();
      }
      return ResponseEntity.ok(comentarios);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
  // /api/comentarios/post/{idPost}
  @GetMapping("/post/{idPost}")
  public ResponseEntity<?> buscarPorPost(@PathVariable Long idPost) {
    try {
      List<Comentarios> comentarios = comentariosService.buscarPorPost(idPost);
      if (comentarios.isEmpty()) {
        return ResponseEntity.noContent().build();
      }
      return ResponseEntity.ok(comentarios);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
  // /api/comentarios/buscar/fechas?fechaInicio=2024-01-01T00:00:00&fechaFin=2024-12-31T23:59:59
  @GetMapping("/buscar/fechas")
  public ResponseEntity<?> buscarPorRangoFechas(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
    try {
      List<Comentarios> comentarios = comentariosService.buscarPorRangoFechas(fechaInicio, fechaFin);
      if (comentarios.isEmpty()) {
        return ResponseEntity.noContent().build();
      }
      return ResponseEntity.ok(comentarios);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
  // /api/comentarios/buscar/despues-de?fecha=2024-01-01T00:00:00
  @GetMapping("/buscar/despues-de")
  public ResponseEntity<List<Comentarios>> buscarCreadosDespuesDe(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha) {
    List<Comentarios> comentarios = comentariosService.buscarCreadosDespuesDe(fecha);
    if (comentarios.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(comentarios);
  }
  // /api/comentarios/buscar/antes-de?fecha=2024-01-01T00:00:00
  @GetMapping("/buscar/antes-de")
  public ResponseEntity<List<Comentarios>> buscarCreadosAntesDe(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha) {
    List<Comentarios> comentarios = comentariosService.buscarCreadosAntesDe(fecha);
    if (comentarios.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(comentarios);
  }
  // /api/comentarios/buscar/longitud-contenido?longitud=5
  @GetMapping("/buscar/longitud-contenido")
  public ResponseEntity<List<Comentarios>> buscarPorLongitudContenido(@RequestParam int longitud) {
    List<Comentarios> comentarios = comentariosService.buscarPorLongitudContenido(longitud);
    if (comentarios.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(comentarios);
  }
  // /api/comentarios/buscar/usuario-post?idUsuario=1&idPost=1
  @GetMapping("/buscar/usuario-post")
  public ResponseEntity<?> buscarPorUsuarioYPost(
      @RequestParam Long idUsuario,
      @RequestParam Long idPost) {
    try {
      List<Comentarios> comentarios = comentariosService.buscarPorUsuarioYPost(idUsuario, idPost);
      if (comentarios.isEmpty()) {
        return ResponseEntity.noContent().build();
      }
      return ResponseEntity.ok(comentarios);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
  // /api/comentarios/contar/post/{idPost}
  @GetMapping("/contar/post/{idPost}")
  public ResponseEntity<?> contarComentariosPorPost(@PathVariable Long idPost) {
    try {
      Long cantidad = comentariosService.contarComentariosPorPost(idPost);
      return ResponseEntity.ok(cantidad);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
  // /api/comentarios/contar/usuario/{idUsuario}
  @GetMapping("/contar/usuario/{idUsuario}")
  public ResponseEntity<?> contarComentariosPorUsuario(@PathVariable Long idUsuario) {
    try {
      Long cantidad = comentariosService.contarComentariosPorUsuario(idUsuario);
      return ResponseEntity.ok(cantidad);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
