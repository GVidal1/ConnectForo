package com.microservicio.usuarios.microservicio_usuarios.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    @NotBlank(message = "El correo no puede estar vacío")
    private String correo;
    @NotBlank(message = "la contraseña no puede estar vacía")
    private String password;
} 