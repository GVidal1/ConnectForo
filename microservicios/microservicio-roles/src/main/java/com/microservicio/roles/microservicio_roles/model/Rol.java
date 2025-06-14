package com.microservicio.roles.microservicio_roles.model;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol", nullable = false, updatable = false, unique = true)
    private Long idRol;

    @Column(name = "tipo_rol", nullable = false, unique = true)
    private String tipoRol;
} 