package com.microservicio.reportes.model;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "reportes")
public class Reporte {
    
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
    
    @NotBlank(message = "El tipo de reporte es obligatorio")
    @Column(name = "tipo_reporte", nullable = false)
    private String tipoReporte;
    
    @Column(name = "id_foro")
    private Long idForo;
    
    @Column(name = "id_categoria")
    private Long idCategoria;
    
    @NotNull(message = "El ID del usuario reportante es obligatorio")
    @Column(name = "id_usuario_reportante", nullable = false)
    private Long idUsuarioReportante;
    
    @Column(name = "id_usuario_reportado")
    private Long idUsuarioReportado;
    
    @NotBlank(message = "El estado es obligatorio")
    @Column(name = "estado", nullable = false)
    private String estado;
    
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        estado = "PENDIENTE";
    }
    
    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
} 