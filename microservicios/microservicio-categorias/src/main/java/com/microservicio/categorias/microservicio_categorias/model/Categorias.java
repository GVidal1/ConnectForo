package com.microservicio.categorias.microservicio_categorias.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categorias")
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Modelo de Categoría para el sistema ConnectForo")
public class Categorias {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "ID único de la categoría", example = "1")
  private Long id;

  @NotBlank(message = "El titúlo no puede estar vacío")
  @Size(max = 120, message = "El titúlo no puede exceder los 120 caracteres")
  @Column(nullable = false, unique = true)
  @Schema(description = "Título de la categoría", example = "Desarrollo Web", required = true)
  private String titulo;

  @Size(max = 550, message = "La descripción no puede exceder los 550 caracteres")
  @Schema(description = "Descripción de la categoría", example = "Categoría para temas relacionados con desarrollo web y tecnologías frontend")
  private String descripcion;

  @Column(name = "fecha_creacion", updatable = false)
  @Schema(description = "Fecha de creación de la categoría", example = "2024-01-15T10:30:00")
  private LocalDateTime fechaCreacion;

  @PrePersist
  protected void creacionFecha() {
      this.fechaCreacion = LocalDateTime.now();
  }

}
