package com.microservicio.comentarios.microservicio_comentarios.model;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comentarios")
@Schema(description = "Modelo de Comentario para el sistema ConnectForo")
public class Comentarios {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "ID único del comentario", example = "1")
  private Long id;

  @NotBlank(message = "El notenido no puede estar vacíó.")
  @Column(nullable = false)
  @Schema(description = "Contenido del comentario", example = "Excelente publicación, muy útil la información", required = true)
  private String contenido;

  // PROTEGIDOS CON UPDATABLE
  @NotNull(message = "El id de la publicación no puede estar vacío.")
  @Column(name = "id_publicacion", nullable = false, updatable = false)
  @Schema(description = "ID de la publicación a la que pertenece el comentario", example = "2", required = true)
  private Long idPost;

  @NotNull(message = "El id del usuario no puede estar vacío.")
  @Column(name = "id_usuario", nullable = false, updatable = false)
  @Schema(description = "ID del usuario que creó el comentario", example = "1", required = true)
  private Long idUsuario;

  @Column(name = "fecha_creacion", updatable = false)
  @Schema(description = "Fecha de creación del comentario", example = "2024-01-15T10:30:00")
  private LocalDateTime fechaCreacion;

  @PrePersist
  private void creacionClase() {
    this.fechaCreacion = LocalDateTime.now();
  }

}
