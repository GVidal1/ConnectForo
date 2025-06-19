package com.microservicio.usuarios.microservicio_usuarios.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CambiarPasswordDTO {
    @NotBlank(message = "La contraseña actual no puede estar vacía")
    private String passwordActual;
    
    @NotBlank(message = "La nueva contraseña no puede estar vacía")
    private String nuevaPassword;
    
    @NotBlank(message = "La confirmación de contraseña no puede estar vacía")
    private String confirmarPassword;
} 