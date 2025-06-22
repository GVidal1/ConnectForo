package com.microservicio.usuarios.microservicio_usuarios.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para cambiar contraseña de usuario")
public class CambiarPasswordDTO {

    @NotBlank(message = "La contraseña actual no puede estar vacía.")
    @Schema(description = "Contraseña actual del usuario", example = "password123")
    private String passwordActual;

    @NotBlank(message = "La nueva contraseña no puede estar vacía.")
    @Schema(description = "Nueva contraseña del usuario", example = "newpassword456")
    private String nuevaPassword;

    @NotBlank(message = "La confirmación de contraseña no puede estar vacía.")
    @Schema(description = "Confirmación de la nueva contraseña", example = "newpassword456")
    private String confirmarPassword;
} 