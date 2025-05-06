package com.microservicio.soporte.model;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "respuestas")
public class Respuesta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "El ID del ticket es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;
    
    @NotNull(message = "El ID del usuario es obligatorio")
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;
    
    @NotBlank(message = "El contenido de la respuesta es obligatorio")
    @Size(min = 1, max = 1000, message = "La respuesta debe tener entre 1 y 1000 caracteres")
    @Column(nullable = false, length = 1000)
    private String contenido;
    
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;
    
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }
} 