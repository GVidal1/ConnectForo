package com.example.ingresar.model;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "login")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class LoginModel {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long idlogin;
    
    @Column(nullable = false, unique = true)
    private String correo;
    
    @Column(nullable = false)
    private String password;

}
