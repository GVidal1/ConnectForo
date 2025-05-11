package com.example.USER.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(name = "nombre usuario", nullable = false, unique = true, length = 100)
    private String userName;

    @Column(nullable = false,length = 40 )
    private String password;

    @Column(name = "Correo Electronico", nullable = false, unique = true , length = 100)
    private String correo;



}
