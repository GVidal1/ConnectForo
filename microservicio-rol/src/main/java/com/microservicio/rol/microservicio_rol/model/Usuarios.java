package com.microservicio.rol.microservicio_rol.model;

import java.time.LocalDateTime;

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


public class Usuarios {

    
    private Long id;


    private String nombreUsuario;



    private String password;

  
    private String correo;

   
    private LocalDateTime fechaCreacion;

    
    protected void creacionFecha() {
        this.fechaCreacion = LocalDateTime.now();
    }
}
