package com.microservicio.roles.microservicio_roles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Long id;
    private Long idRol;
    private String nombreUsuario;
    private String correo;
} 