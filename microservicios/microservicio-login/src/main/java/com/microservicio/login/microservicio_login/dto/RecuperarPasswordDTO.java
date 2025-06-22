package com.microservicio.login.microservicio_login.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para recuperación de contraseña")
public class RecuperarPasswordDTO {
    
    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "El correo debe tener un formato válido")
    @Schema(description = "Correo electrónico del usuario para recuperar contraseña", 
            example = "correo@test.com", required = true)
    private String correo;
} 