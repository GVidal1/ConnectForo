package com.microservicio.interacciones.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "interacciones")
public class Interaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El tipo de interacción es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoInteraccion tipo;

    @NotNull(message = "El ID del usuario es obligatorio")
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "publicacion_id")
    private Long publicacionId;

    @Column(name = "comentario_id")
    private Long comentarioId;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }

    public enum TipoInteraccion {
        LIKE,
        DISLIKE
    }
} 