package com.microservicio.post.microservicio_post.controller;

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

import com.microservicio.post.microservicio_post.model.Post;
import com.microservicio.post.microservicio_post.services.PostService;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;

@RestController
@RequestMapping("/api/publicaciones")
public class PostController {

  @Autowired
  private PostService postService;

  @GetMapping()
  public ResponseEntity<List<Post>> listarPublicaciones() {
    List<Post> listaPost = postService.listarPublicaciones();
    if ( listaPost.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(listaPost);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> obtenerPublicacion(@PathVariable Long id) {
    try {
      Post postEncontrado = postService.buscarPublicacionPorId(id);
      return ResponseEntity.ok(postEncontrado);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }

  @PostMapping()
  public ResponseEntity<?> crearPublicacion(@RequestBody @Valid Post publicacion) {
    try {
      Post publicacionGuardada = postService.guardarPublicacion(publicacion);
      return ResponseEntity.status(HttpStatus.CREATED).body(publicacionGuardada);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body("Error al crear la publicacion: " + e.getMessage());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> actualizarPublicacion(
    @PathVariable Long id,
    @RequestBody @Valid Post publicacion) {
      try {
        Post postActual = postService.actualizarPost(id, publicacion);
        return ResponseEntity.ok(postActual);
      } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body("Error al actualizar la publicación: " + e.getMessage());
      }
    }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> borrarPublicacion(@PathVariable Long id) {
      try {
        String postActual = postService.borrarPubliacionPorId(id);
        return ResponseEntity.ok(postActual);

      } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
      }
    }
  // /api/publicaciones/buscar/titulo?palabra=React
  @GetMapping("/buscar/titulo")
  public ResponseEntity<List<Post>> buscarPorPalabraEnTitulo(@RequestParam String palabra) {
    List<Post> posts = postService.buscarPorPalabraEnTitulo(palabra);
    if (posts.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(posts);
  }
  // /api/publicaciones/buscar/contenido?palabra=Desarrollo
  @GetMapping("/buscar/contenido")
  public ResponseEntity<List<Post>> buscarPorPalabraEnContenido(@RequestParam String palabra) {
    List<Post> posts = postService.buscarPorPalabraEnContenido(palabra);
    if (posts.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(posts);
  }
  // /api/publicaciones/usuario/{idUsuario}
  @GetMapping("/usuario/{idUsuario}")
  public ResponseEntity<?> buscarPorUsuario(@PathVariable Long idUsuario) {
    try {
      List<Post> posts = postService.buscarPorUsuario(idUsuario);
      if (posts.isEmpty()) {
        return ResponseEntity.noContent().build();
      }
      return ResponseEntity.ok(posts);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
  // /api/publicaciones/foro/{idForo}
  @GetMapping("/foro/{idForo}")
  public ResponseEntity<?> buscarPorForo(@PathVariable Long idForo) {
    try {
      List<Post> posts = postService.buscarPorForo(idForo);
      if (posts.isEmpty()) {
        return ResponseEntity.noContent().build();
      }
      return ResponseEntity.ok(posts);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
  // /api/publicaciones/buscar/fechas?fechaInicio=2024-01-01T00:00:00&fechaFin=2024-12-31T23:59:59
  @GetMapping("/buscar/fechas")
  public ResponseEntity<?> buscarPorRangoFechas(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
    try {
      List<Post> posts = postService.buscarPorRangoFechas(fechaInicio, fechaFin);
      if (posts.isEmpty()) {
        return ResponseEntity.noContent().build();
      }
      return ResponseEntity.ok(posts);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
  // /api/publicaciones/buscar/despues-de?fecha=2024-01-01T00:00:00
  @GetMapping("/buscar/despues-de")
  public ResponseEntity<List<Post>> buscarCreadosDespuesDe(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha) {
    List<Post> posts = postService.buscarCreadosDespuesDe(fecha);
    if (posts.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(posts);
  }
  // /api/publicaciones/buscar/antes-de?fecha=2024-01-01T00:00:00
  @GetMapping("/buscar/antes-de")
  public ResponseEntity<List<Post>> buscarCreadosAntesDe(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha) {
    List<Post> posts = postService.buscarCreadosAntesDe(fecha);
    if (posts.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(posts);
  }
  // /api/publicaciones/buscar/longitud-titulo?longitud=5
  @GetMapping("/buscar/longitud-titulo")
  public ResponseEntity<List<Post>> buscarPorLongitudTitulo(@RequestParam int longitud) {
    List<Post> posts = postService.buscarPorLongitudTitulo(longitud);
    if (posts.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(posts);
  }
  // /api/publicaciones/buscar/usuario-foro?idUsuario=1&idForo=1
  @GetMapping("/buscar/usuario-foro")
  public ResponseEntity<?> buscarPorUsuarioYForo(
      @RequestParam Long idUsuario,
      @RequestParam Long idForo) {
    try {
      List<Post> posts = postService.buscarPorUsuarioYForo(idUsuario, idForo);
      if (posts.isEmpty()) {
        return ResponseEntity.noContent().build();
      }
      return ResponseEntity.ok(posts);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
