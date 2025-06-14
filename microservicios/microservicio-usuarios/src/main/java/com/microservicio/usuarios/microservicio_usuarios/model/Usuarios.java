package com.microservicio.usuarios.microservicio_usuarios.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "El id del rol no puede estar vacio")
    private Long idRol;

    @NotBlank(message = "El nombre de usuario no puede estar vacío.")
    @Size(min = 3, max = 60, message = "El nombre de usuario debe contener entre 3 y 60 caracteres.")
    @Column(name = "nombre_usuario", nullable = false, unique = true, length = 100)
    private String nombreUsuario;

    @NotBlank(message = "El correo no puede estar vacío.")
    @Email(message = "El correo debe tener un formato válido.")
    @Column(name = "correo", nullable = false, unique = true)
    private String correo;

    @NotBlank(message = "La contraseña no puede estar vacía.")
    @Size(min = 3, max = 100, message = "La contraseña debe contener entre 3 y 100 caracteres (encriptada).")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false, length = 100)
    private String password;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    private String nombre;

    private String apellidos;

    @PrePersist
    protected void inicializacionDatos() {
        this.fechaCreacion = LocalDateTime.now();
        this.nombre = null;
        this.apellidos = null;
    }
} 