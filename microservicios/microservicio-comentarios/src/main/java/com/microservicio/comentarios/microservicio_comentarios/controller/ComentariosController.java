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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/comentarios")
@Tag(name = "Comentarios", description = "API para gestión de comentarios en ConnectForo")
public class ComentariosController {

  @Autowired
  private ComentariosService comentariosService;

  @Operation(summary = "Listar todos los comentarios", description = "Obtiene una lista de todos los comentarios disponibles")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Lista de comentarios obtenida exitosamente",
      content = @Content(mediaType = "application/json", 
      schema = @Schema(implementation = Comentarios.class))),
    @ApiResponse(responseCode = "204", description = "No hay comentarios disponibles")
  })
  @GetMapping()
  public ResponseEntity<List<Comentarios>> listaDeComentarios() {
    List<Comentarios> comentariosExistentes = comentariosService.listarComentarios();
    if (comentariosExistentes.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(comentariosExistentes);
  }

  @Operation(summary = "Buscar comentario por ID", description = "Obtiene un comentario específico por su ID")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Comentario encontrado exitosamente",
      content = @Content(mediaType = "application/json", 
      schema = @Schema(implementation = Comentarios.class))),
    @ApiResponse(responseCode = "404", description = "Comentario no encontrado")
  })
  @GetMapping("/{id}")
  public ResponseEntity<?> buscarComentarioPorId(@PathVariable Long id) {
    try {
      Comentarios comentarioAct = comentariosService.buscarComentarioPorId(id);
      return ResponseEntity.ok(comentarioAct);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }

  @Operation(summary = "Eliminar comentario", description = "Elimina un comentario del sistema por su ID")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "204", description = "Comentario eliminado exitosamente"),
    @ApiResponse(responseCode = "404", description = "Comentario no encontrado")
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<?> eliminarComentarioPorId(@PathVariable Long id) {
    try {
      String comentarioAct = comentariosService.borrarComentarioPorId(id);
      return ResponseEntity.noContent().build();
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }

  @Operation(summary = "Crear nuevo comentario", description = "Crea un nuevo comentario en el sistema")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Comentario creado exitosamente",
      content = @Content(mediaType = "application/json", 
      schema = @Schema(implementation = Comentarios.class))),
    @ApiResponse(responseCode = "400", description = "Error al crear el comentario")
  })
  @PostMapping()
  public ResponseEntity<?> crearNuevoComentario(@RequestBody @Valid Comentarios comentarioNuevo) {
    try {
      Comentarios comentario = comentariosService.guardarComentario(comentarioNuevo);
      return ResponseEntity.status(HttpStatus.CREATED).body(comentario);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No es posible crear el comentario: " + e.getMessage());
    }
  }

  @Operation(summary = "Actualizar comentario", description = "Actualiza la información de un comentario existente")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Comentario actualizado exitosamente",
      content = @Content(mediaType = "application/json", 
      schema = @Schema(implementation = Comentarios.class))),
    @ApiResponse(responseCode = "400", description = "Error al actualizar el comentario")
  })
  @PutMapping("/{id}")
  public ResponseEntity<?> actualizarComentarioPorId(
    @PathVariable Long id,
    @RequestBody @Valid Comentarios comentarioActualizado
    ) {
      try {
        Comentarios comentario = comentariosService.actualizarComentario(id, comentarioActualizado);
        return ResponseEntity.ok(comentario);
      } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body("Error al actualizar el comentario: " + e.getMessage());
      }
    }

  // /api/comentarios/buscar/contenido?palabra=Desarrollo
  @Operation(summary = "Buscar comentarios por palabra en contenido", description = "Busca comentarios cuyo contenido contenga la palabra especificada")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Comentarios encontrados exitosamente",
      content = @Content(mediaType = "application/json", 
      schema = @Schema(implementation = Comentarios.class))),
    @ApiResponse(responseCode = "204", description = "No se encontraron comentarios")
  })
  @GetMapping("/buscar/contenido")
  public ResponseEntity<List<Comentarios>> buscarPorPalabraEnContenido(@RequestParam String palabra) {
    List<Comentarios> comentarios = comentariosService.buscarPorPalabraEnContenido(palabra);
    if (comentarios.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(comentarios);
  }

  // /api/comentarios/usuario/{idUsuario}
  @Operation(summary = "Buscar comentarios por usuario", description = "Obtiene todos los comentarios creados por un usuario específico")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Comentarios encontrados exitosamente",
      content = @Content(mediaType = "application/json", 
      schema = @Schema(implementation = Comentarios.class))),
    @ApiResponse(responseCode = "204", description = "No se encontraron comentarios para este usuario"),
    @ApiResponse(responseCode = "400", description = "Error en la búsqueda")
  })
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
  @Operation(summary = "Buscar comentarios por post", description = "Obtiene todos los comentarios de un post específico")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Comentarios encontrados exitosamente",
      content = @Content(mediaType = "application/json", 
      schema = @Schema(implementation = Comentarios.class))),
    @ApiResponse(responseCode = "204", description = "No se encontraron comentarios para este post"),
    @ApiResponse(responseCode = "400", description = "Error en la búsqueda")
  })
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
  @Operation(summary = "Buscar comentarios por rango de fechas", description = "Busca comentarios creados dentro del rango de fechas especificado")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Comentarios encontrados exitosamente",
      content = @Content(mediaType = "application/json", 
      schema = @Schema(implementation = Comentarios.class))),
    @ApiResponse(responseCode = "204", description = "No se encontraron comentarios"),
    @ApiResponse(responseCode = "400", description = "Error en la búsqueda")
  })
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
  @Operation(summary = "Buscar comentarios creados después de una fecha", description = "Busca comentarios creados después de la fecha especificada")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Comentarios encontrados exitosamente",
      content = @Content(mediaType = "application/json", 
      schema = @Schema(implementation = Comentarios.class))),
    @ApiResponse(responseCode = "204", description = "No se encontraron comentarios")
  })
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
  @Operation(summary = "Buscar comentarios creados antes de una fecha", description = "Busca comentarios creados antes de la fecha especificada")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Comentarios encontrados exitosamente",
      content = @Content(mediaType = "application/json", 
      schema = @Schema(implementation = Comentarios.class))),
    @ApiResponse(responseCode = "204", description = "No se encontraron comentarios")
  })
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
  @Operation(summary = "Buscar comentarios por longitud de contenido", description = "Busca comentarios cuyo contenido tenga la longitud especificada")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Comentarios encontrados exitosamente",
      content = @Content(mediaType = "application/json", 
      schema = @Schema(implementation = Comentarios.class))),
    @ApiResponse(responseCode = "204", description = "No se encontraron comentarios")
  })
  @GetMapping("/buscar/longitud-contenido")
  public ResponseEntity<List<Comentarios>> buscarPorLongitudContenido(@RequestParam int longitud) {
    List<Comentarios> comentarios = comentariosService.buscarPorLongitudContenido(longitud);
    if (comentarios.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(comentarios);
  }

  // /api/comentarios/buscar/usuario-post?idUsuario=1&idPost=1
  @Operation(summary = "Buscar comentarios por usuario y post", description = "Busca comentarios de un post específico creados por un usuario específico")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Comentarios encontrados exitosamente",
      content = @Content(mediaType = "application/json", 
      schema = @Schema(implementation = Comentarios.class))),
    @ApiResponse(responseCode = "204", description = "No se encontraron comentarios"),
    @ApiResponse(responseCode = "400", description = "Error en la búsqueda")
  })
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
  @Operation(summary = "Contar comentarios por post", description = "Obtiene el número total de comentarios de un post específico")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Cantidad de comentarios obtenida exitosamente",
      content = @Content(mediaType = "application/json", 
      examples = @ExampleObject(value = "5"))),
    @ApiResponse(responseCode = "400", description = "Error al contar comentarios")
  })
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
  @Operation(summary = "Contar comentarios por usuario", description = "Obtiene el número total de comentarios creados por un usuario específico")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Cantidad de comentarios obtenida exitosamente",
      content = @Content(mediaType = "application/json", 
      examples = @ExampleObject(value = "10"))),
    @ApiResponse(responseCode = "400", description = "Error al contar comentarios")
  })
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
