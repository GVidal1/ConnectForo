package com.example.USER.model;

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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de usuario no puede estar vacio")
    @Size(min = 3, max = 60, message = "El nombre de usuario debe de contener entre 3 a 60 caracteres.")
    @Column(name = "nombre_usuario", nullable = false, unique = true, length = 100)
    private String nombreUsario;

    @NotBlank(message = "La contraseña no puede estar vacía.")
    @Size(min = 3, max = 55, message = "La contraseña debe contener entre 3 a 55 caracteres.")
    @Column(nullable = false, length = 40 )
    private String password;

    @NotBlank(message = "El correo es un campo Obligatio. No puede estar vacio.")
    @Column(name = "correo", nullable = false, unique = true )
    private String correo;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    protected void creacionFecha() {
        this.fechaCreacion = LocalDateTime.now();
    }
}
