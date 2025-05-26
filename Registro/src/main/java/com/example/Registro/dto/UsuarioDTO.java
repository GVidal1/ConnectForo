package com.example.Registro.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UsuarioDTO {
    private Long idRol;
    private String nombreUsuario;
    private String password;
    private String correo;
    private LocalDateTime fechaCreacion;

    public UsuarioDTO(String nombreUsuario, String password, String correo) {
        this.idRol = 5L;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.correo = correo;
        this.fechaCreacion = LocalDateTime.now();
    }
}