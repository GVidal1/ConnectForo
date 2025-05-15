package com.microservicio.anuncios.model;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "anuncios")
public class Anuncio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El mensaje del anuncio es obligatorio")
    @Size(min = 3, max = 1000, message = "El mensaje debe tener entre 3 y 1000 caracteres")
    @Column(nullable = false, length = 1000)
    private String mensaje;

    @NotNull(message = "El ID del moderador es obligatorio")
    @Column(name = "moderador_id", nullable = false)
    private Long moderadorId;

    @Column(name = "foro_id")
    private Long foroId;

    @Column(name = "publicacion_id")
    private Long publicacionId;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }
} 