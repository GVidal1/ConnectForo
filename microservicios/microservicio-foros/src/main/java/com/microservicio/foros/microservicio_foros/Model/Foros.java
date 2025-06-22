package com.microservicio.foros.microservicio_foros.Model;

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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "foros")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Modelo de Foro para el sistema ConnectForo")
public class Foros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del foro", example = "1")
    private Long id;

    //Id de otro microservicio para luego buscarlo y llamarlo :)
    @NotNull(message = "El id de categoria no puede estar vacío")
    @Column(name = "id_categoria", nullable = false)
    @Schema(description = "ID de la categoría a la que pertenece el foro", example = "2", required = true)
    private Long idCategoria;

    @NotNull(message = "El id del usuario creador no puede estar vacío")
    @Column(name = "id_usuario_creador", nullable = false)
    @Schema(description = "ID del usuario creador del foro", example = "1", required = true)
    private Long idUsuarioCreador;

    @NotBlank(message = "El título no puede estar vacío")
    @Size(max = 100, message = "El título no puede exceder los 100 caracteres")
    @Column(nullable = false, unique = true)
    @Schema(description = "Título del foro", example = "Foro de Desarrollo Web", required = true)
    private String titulo;

    @Size(max = 500, message = "La descripción no puede exceder los 5000 caracteres")
    @Schema(description = "Contenido del foro", example = "Este es un foro para discutir temas relacionados con el desarrollo web y las mejores prácticas")
    private String contenido;

    @Column(name = "fecha_creacion", updatable = false)
    @Schema(description = "Fecha de creación del foro", example = "2024-01-15T10:30:00")
    private LocalDateTime fechaCreacion;

    @PrePersist
    protected void creacionFecha() {
        this.fechaCreacion = LocalDateTime.now();
    }
}