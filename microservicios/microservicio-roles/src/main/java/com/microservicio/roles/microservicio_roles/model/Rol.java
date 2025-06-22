package com.microservicio.roles.microservicio_roles.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "roles")
@Schema(description = "Modelo de Rol para el sistema ConnectForo")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol", nullable = false, updatable = false, unique = true)
    @Schema(description = "ID único del rol", example = "1")
    private Long idRol;

    @Column(name = "tipo_rol", nullable = false, unique = true)
    @NotBlank(message = "El tipo de rol es obligatorio")
    @Size(min = 2, max = 50, message = "El tipo de rol debe tener entre 2 y 50 caracteres")
    @Schema(description = "Tipo de rol del usuario", example = "ADMIN", 
            allowableValues = {"ADMIN", "MODERADOR", "USUARIO", "SOPORTE", "GESTOR_ANUNCIOS"})
    private String tipoRol;
} 