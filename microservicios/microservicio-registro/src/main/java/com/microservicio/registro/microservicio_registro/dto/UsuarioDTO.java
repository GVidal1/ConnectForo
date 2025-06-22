package com.microservicio.registro.microservicio_registro.dto;

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
@Schema(description = "DTO para registro de nuevos usuarios")
public class UsuarioDTO {
    
    @NotNull(message = "El ID del rol es obligatorio")
    @Schema(description = "ID del rol asignado al usuario", example = "2", required = true)
    private Long idRol;
    
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres")
    @Schema(description = "Nombre de usuario único", example = "usuarioTest", required = true)
    private String nombreUsuario;
    
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato válido")
    @Schema(description = "Correo electrónico del usuario", example = "correo@test.com", required = true)
    private String correo;
    
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    @Schema(description = "Contraseña del usuario", example = "passwordTest123", required = true)
    private String password;
} 