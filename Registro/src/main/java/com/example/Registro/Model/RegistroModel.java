package com.example.Registro.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "registro")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es un campo Obligatio. No puede estar vacio.")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El apellido es un campo Obligatio. No puede estar vacio.")
    @Column(nullable = false)
    private String apellido;

    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    
    @NotBlank(message = "El correo es un campo Obligatio. No puede estar vacio.")
    @Column(name = "correo", nullable = false, unique = true )
    private String correo;

    @NotBlank(message = "La contraseña no puede estar vacía.")
    @Size(min = 3, max = 55, message = "La contraseña debe contener entre 3 a 55 caracteres.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;
}

