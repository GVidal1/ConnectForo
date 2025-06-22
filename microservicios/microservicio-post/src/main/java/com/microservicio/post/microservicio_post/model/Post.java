package com.microservicio.post.microservicio_post.model;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "publicaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Modelo de Publicación para el sistema ConnectForo")
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "ID único de la publicación", example = "1")
  private Long id;

  @NotBlank(message = "El titulo no puede estar vacío.")
  @Size(min = 1, max = 120, message = "El titulo debe estar entre 1 a 120 caracteres.")
  @Column(nullable = false)
  @Schema(description = "Título de la publicación", example = "Mi primera publicacion", required = true)
  private String titulo;

  @NotBlank(message = "El contenido no puede estar vacío")
  @Lob  
  @Column(nullable = false)
  @Schema(description = "Contenido de la publicación", example = "Este es el contenido de mi primera publicación en el foro", required = true)
  private String contenido;

  @NotNull(message = "El ID del foro no puede ser nulo")
  @Column(name = "id_foro", nullable = false, updatable = false)
  @Schema(description = "ID del foro al que pertenece la publicación", example = "2", required = true)
  private Long idForo;

  @NotNull(message = "El ID del usuario no puede ser nulo")
  @Column(name = "id_usuario", nullable = false, updatable = false)
  @Schema(description = "ID del usuario que creó la publicación", example = "1", required = true)
  private Long idUsuario;

  @Column(name = "fecha_creacion", updatable = false)
  @Schema(description = "Fecha de creación de la publicación", example = "2024-01-15T10:30:00")
  private LocalDateTime fechaCreacion;

  @PrePersist
  protected void creacionFecha() {
      this.fechaCreacion = LocalDateTime.now();
  }
}
