package com.microservicio.anuncios.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

@Data
@AllArgsConstructor
@NoArgsConstructor
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