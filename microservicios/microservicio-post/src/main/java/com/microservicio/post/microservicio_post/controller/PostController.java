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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;

@RestController
@RequestMapping("/api/publicaciones")
@Tag(name = "Publicaciones", description = "API para gestión de publicaciones en ConnectForo")
public class PostController {

  @Autowired
  private PostService postService;

  @Operation(summary = "Listar todas las publicaciones", description = "Obtiene una lista de todas las publicaciones disponibles")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Lista de publicaciones obtenida exitosamente",
      content = @Content(mediaType = "application/json", 
      schema = @Schema(implementation = Post.class))),
    @ApiResponse(responseCode = "204", description = "No hay publicaciones disponibles")
  })
  @GetMapping()
  public ResponseEntity<List<Post>> listarPublicaciones() {
    List<Post> listaPost = postService.listarPublicaciones();
    if ( listaPost.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(listaPost);
  }

  @Operation(summary = "Buscar publicación por ID", description = "Obtiene una publicación específica por su ID")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Publicación encontrada exitosamente",
      content = @Content(mediaType = "application/json", 
      schema = @Schema(implementation = Post.class))),
    @ApiResponse(responseCode = "404", description = "Publicación no encontrada")
  })
  @GetMapping("/{id}")
  public ResponseEntity<?> obtenerPublicacion(@PathVariable Long id) {
    try {
      Post postEncontrado = postService.buscarPublicacionPorId(id);
      return ResponseEntity.ok(postEncontrado);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }

  @Operation(summary = "Crear nueva publicación", description = "Crea una nueva publicación en el sistema")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Publicación creada exitosamente",
      content = @Content(mediaType = "application/json", 
      schema = @Schema(implementation = Post.class))),
    @ApiResponse(responseCode = "400", description = "Error al crear la publicación")
  })
  @PostMapping()
  public ResponseEntity<?> crearPublicacion(@RequestBody @Valid Post publicacion) {
    try {
      Post publicacionGuardada = postService.guardarPublicacion(publicacion);
      return ResponseEntity.status(HttpStatus.CREATED).body(publicacionGuardada);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body("Error al crear la publicacion: " + e.getMessage());
    }
  }

  @Operation(summary = "Actualizar publicación", description = "Actualiza la información de una publicación existente")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Publicación actualizada exitosamente",
      content = @Content(mediaType = "application/json", 
      schema = @Schema(implementation = Post.class))),
    @ApiResponse(responseCode = "400", description = "Error al actualizar la publicación")
  })
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

  @Operation(summary = "Eliminar publicación", description = "Elimina una publicación del sistema por su ID")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Publicación eliminada exitosamente"),
    @ApiResponse(responseCode = "404", description = "Publicación no encontrada")
  })
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
  @Operation(summary = "Buscar publicaciones por palabra en título", description = "Busca publicaciones cuyo título contenga la palabra especificada")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Publicaciones encontradas exitosamente",
      content = @Content(mediaType = "application/json", 
      schema = @Schema(implementation = Post.class))),
    @ApiResponse(responseCode = "204", description = "No se encontraron publicaciones")
  })
  @GetMapping("/buscar/titulo")
  public ResponseEntity<List<Post>> buscarPorPalabraEnTitulo(@RequestParam String palabra) {
    List<Post> posts = postService.buscarPorPalabraEnTitulo(palabra);
    if (posts.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(posts);
  }
  // /api/publicaciones/buscar/contenido?palabra=Desarrollo
  @Operation(summary = "Buscar publicaciones por palabra en contenido", description = "Busca publicaciones cuyo contenido contenga la palabra especificada")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Publicaciones encontradas exitosamente",
      content = @Content(mediaType = "application/json", 
      schema = @Schema(implementation = Post.class))),
    @ApiResponse(responseCode = "204", description = "No se encontraron publicaciones")
  })
  @GetMapping("/buscar/contenido")
  public ResponseEntity<List<Post>> buscarPorPalabraEnContenido(@RequestParam String palabra) {
    List<Post> posts = postService.buscarPorPalabraEnContenido(palabra);
    if (posts.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(posts);
  }
  // /api/publicaciones/usuario/{idUsuario}
  @Operation(summary = "Buscar publicaciones por usuario", description = "Obtiene todas las publicaciones creadas por un usuario específico")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Publicaciones encontradas exitosamente",
      content = @Content(mediaType = "application/json", 
      schema = @Schema(implementation = Post.class))),
    @ApiResponse(responseCode = "204", description = "No se encontraron publicaciones para este usuario"),
    @ApiResponse(responseCode = "400", description = "Error en la búsqueda")
  })
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
  @Operation(summary = "Buscar publicaciones por foro", description = "Obtiene todas las publicaciones de un foro específico")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Publicaciones encontradas exitosamente",
      content = @Content(mediaType = "application/json", 
      schema = @Schema(implementation = Post.class))),
    @ApiResponse(responseCode = "204", description = "No se encontraron publicaciones para este foro"),
    @ApiResponse(responseCode = "400", description = "Error en la búsqueda")
  })
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
  @Operation(summary = "Buscar publicaciones por rango de fechas", description = "Busca publicaciones creadas dentro del rango de fechas especificado")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Publicaciones encontradas exitosamente",
      content = @Content(mediaType = "application/json", 
      schema = @Schema(implementation = Post.class))),
    @ApiResponse(responseCode = "204", description = "No se encontraron publicaciones"),
    @ApiResponse(responseCode = "400", description = "Error en la búsqueda")
  })
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
  @Operation(summary = "Buscar publicaciones creadas después de una fecha", description = "Busca publicaciones creadas después de la fecha especificada")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Publicaciones encontradas exitosamente",
      content = @Content(mediaType = "application/json", 
      schema = @Schema(implementation = Post.class))),
    @ApiResponse(responseCode = "204", description = "No se encontraron publicaciones")
  })
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
  @Operation(summary = "Buscar publicaciones creadas antes de una fecha", description = "Busca publicaciones creadas antes de la fecha especificada")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Publicaciones encontradas exitosamente",
      content = @Content(mediaType = "application/json", 
      schema = @Schema(implementation = Post.class))),
    @ApiResponse(responseCode = "204", description = "No se encontraron publicaciones")
  })
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
  @Operation(summary = "Buscar publicaciones por longitud de título", description = "Busca publicaciones cuyo título tenga la longitud especificada")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Publicaciones encontradas exitosamente",
      content = @Content(mediaType = "application/json", 
      schema = @Schema(implementation = Post.class))),
    @ApiResponse(responseCode = "204", description = "No se encontraron publicaciones")
  })
  @GetMapping("/buscar/longitud-titulo")
  public ResponseEntity<List<Post>> buscarPorLongitudTitulo(@RequestParam int longitud) {
    List<Post> posts = postService.buscarPorLongitudTitulo(longitud);
    if (posts.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(posts);
  }
  // /api/publicaciones/buscar/usuario-foro?idUsuario=1&idForo=1
  @Operation(summary = "Buscar publicaciones por usuario y foro", description = "Busca publicaciones de un foro específico creadas por un usuario específico")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Publicaciones encontradas exitosamente",
      content = @Content(mediaType = "application/json", 
      schema = @Schema(implementation = Post.class))),
    @ApiResponse(responseCode = "204", description = "No se encontraron publicaciones"),
    @ApiResponse(responseCode = "400", description = "Error en la búsqueda")
  })
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
