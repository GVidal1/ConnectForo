package com.microservicio.registro.microservicio_registro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRespuestaDTO {
    private Long idRol;
    private String nombreUsuario;
    private String correo;
} 