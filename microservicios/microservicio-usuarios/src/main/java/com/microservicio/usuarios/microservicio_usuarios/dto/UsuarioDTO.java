package com.microservicio.usuarios.microservicio_usuarios.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para crear un nuevo usuario")
public class UsuarioDTO {

    @NotNull(message = "El id del rol no puede estar vacío")
    @Schema(description = "ID del rol asignado al usuario", example = "1")
    private Long idRol;

    @NotBlank(message = "El nombre de usuario no puede estar vacío.")
    @Size(min = 3, max = 60, message = "El nombre de usuario debe contener entre 3 y 60 caracteres.")
    @Schema(description = "Nombre de usuario único", example = "usuarioTest", minLength = 3, maxLength = 60)
    private String nombreUsuario;

    @NotBlank(message = "El correo no puede estar vacío.")
    @Email(message = "El correo debe tener un formato válido.")
    @Schema(description = "Correo electrónico del usuario", example = "correo@test.com")
    private String correo;

    @NotBlank(message = "La contraseña no puede estar vacía.")
    @Size(min = 3, max = 100, message = "La contraseña debe contener entre 3 y 100 caracteres.")
    @Schema(description = "Contraseña del usuario", example = "password123", minLength = 3, maxLength = 100)
    private String password;
} 