package com.microservicio.usuarios.microservicio_usuarios.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Modelo de Usuario del sistema")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del usuario", example = "1")
    private Long id;

    @NotNull(message = "El id del rol no puede estar vacio")
    @Schema(description = "ID del rol asignado al usuario", example = "1")
    private Long idRol;

    @NotBlank(message = "El nombre de usuario no puede estar vacío.")
    @Size(min = 3, max = 60, message = "El nombre de usuario debe contener entre 3 y 60 caracteres.")
    @Column(name = "nombre_usuario", nullable = false, unique = true, length = 100)
    @Schema(description = "Nombre de usuario único", example = "usuarioTest", minLength = 3, maxLength = 60)
    private String nombreUsuario;

    @NotBlank(message = "El correo no puede estar vacío.")
    @Email(message = "El correo debe tener un formato válido.")
    @Column(name = "correo", nullable = false, unique = true)
    @Schema(description = "Correo electrónico del usuario", example = "correo@test.com")
    private String correo;

    @NotBlank(message = "La contraseña no puede estar vacía.")
    @Size(min = 3, max = 100, message = "La contraseña debe contener entre 3 y 100 caracteres (encriptada).")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false, length = 100)
    @Schema(description = "Contraseña del usuario (encriptada)", example = "password123", minLength = 3, maxLength = 100)
    private String password;

    @Column(name = "fecha_creacion", updatable = false)
    @Schema(description = "Fecha de creación del usuario", example = "2024-01-15T10:30:00")
    private LocalDateTime fechaCreacion;

    @Schema(description = "Nombre real del usuario", example = "test")
    private String nombre;

    @Schema(description = "Apellidos del usuario", example = "usuario")
    private String apellidos;

    @PrePersist
    protected void inicializacionDatos() {
        this.fechaCreacion = LocalDateTime.now();
        this.nombre = null;
        this.apellidos = null;
    }
} 