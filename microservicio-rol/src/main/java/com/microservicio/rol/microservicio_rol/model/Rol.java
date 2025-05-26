package com.microservicio.rol.microservicio_rol.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "roles")
public class Rol {

    public enum TipoRol {
        ADMIN,
        MODERADOR,
        SOPORTE,
        GESTOR_ANUNCIOS,
        USUARIO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol", nullable = false, updatable = false, unique = false)
    private Long idRol;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_rol", nullable = false)
    private TipoRol tipoRol;
}
