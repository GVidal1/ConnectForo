package com.example.Registro.Model;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name="registro") 

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroModel {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer idRegistro;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false)
    private Date fechaNacimiento;

    @Column(nullable = true)
    private String correo;

    @Column(nullable = true)
    private String contraseña;



}
