package com.microservicio.roles.microservicio_roles.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para transferencia de datos de usuario")
public class UsuarioDTO {
    
    @Schema(description = "ID único del usuario", example = "1")
    private Long id;
    
    @Schema(description = "ID del rol asignado al usuario", example = "2")
    private Long idRol;
    
    @Schema(description = "Nombre de usuario", example = "usuarioTest")
    private String nombreUsuario;
    
    @Schema(description = "Correo electrónico del usuario", example = "correo@test.com")
    private String correo;
} 