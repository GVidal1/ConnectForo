package com.example.USER.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.USER.model.Usuarios;
import com.example.USER.repository.UsuarioRepository;

@Configuration
public class CargarDatos {

    @Bean
    CommandLineRunner initDataBase(UsuarioRepository usuariosRepository) {
        return args -> {
            // Solo se ejecuta si no hay usuarios en la base de datos
            if (usuariosRepository.count() == 0) {
                // Insertamos los usuarios con los datos proporcionados
                usuariosRepository.save(new Usuarios(null, 1L, "gabriel", "password123", "gabriel@duocuc.cl", null));
                usuariosRepository.save(new Usuarios(null, 2L, "francisco", "1234secure", "fran@gmail.com", null));
                usuariosRepository.save(new Usuarios(null, 3L, "mauricio", "miClaveSegura", "maur@gmail.com", null));
                usuariosRepository.save(new Usuarios(null, 4L, "riskoder", "clave123", "risk@gmail.com", null));
                usuariosRepository.save(new Usuarios(null, 5L, "Anonimus", "pass5678", "pedroramirez@example.com", null));

                System.out.println("Datos de usuarios cargados correctamente.");
            }
        };
    }
}
