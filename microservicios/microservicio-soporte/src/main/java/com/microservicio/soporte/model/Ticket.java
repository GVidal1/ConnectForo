package com.microservicio.soporte.model;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tickets")
public class Ticket {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El título es obligatorio")
    @Size(min = 3, max = 100, message = "El título debe tener entre 3 y 100 caracteres")
    @Column(nullable = false)
    private String titulo;
    
    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 10, max = 1000, message = "La descripción debe tener entre 10 y 1000 caracteres")
    @Column(nullable = false, length = 1000)
    private String descripcion;
    
    @NotNull(message = "El ID del usuario es obligatorio")
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;
    
    @Column(name = "moderador_id")
    private Long moderadorId;
    
    @Enumerated(EnumType.STRING)
    @NotNull(message = "El estado del ticket es obligatorio")
    @Column(nullable = false)
    private EstadoTicket estado;
    
    @Enumerated(EnumType.STRING)
    @NotNull(message = "La categoría del ticket es obligatoria")
    @Column(nullable = false)
    private CategoriaTicket categoria;
    
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        if (estado == null) {
            estado = EstadoTicket.ABIERTO;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
    
    public enum EstadoTicket {
        ABIERTO,
        EN_PROCESO,
        RESUELTO,
        CERRADO
    }
    
    public enum CategoriaTicket {
        TECNICO,
        CUENTA,
        CONTENIDO,
        OTRO
    }
} 