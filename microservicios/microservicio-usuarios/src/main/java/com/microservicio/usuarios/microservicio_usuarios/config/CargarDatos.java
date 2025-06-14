package com.microservicio.usuarios.microservicio_usuarios.config;

import com.microservicio.usuarios.microservicio_usuarios.model.Usuarios;
import com.microservicio.usuarios.microservicio_usuarios.repository.UsuarioRepository;
import com.microservicio.usuarios.microservicio_usuarios.service.UserService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CargarDatos {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner initDataBase(UserService userService, PasswordEncoder encoder, UsuarioRepository usuarioRepository) {
        return args -> {
            if (usuarioRepository.count() == 0) {
                userService.guardarUsuario(new Usuarios(null, 1L, "gabriel", "gabriel@duocuc.cl", encoder.encode("password123"), null, null, null));
                userService.guardarUsuario(new Usuarios(null, 2L, "francisco", "fran@gmail.com", encoder.encode("1234secure"), null, null, null));
                userService.guardarUsuario(new Usuarios(null, 3L, "mauricio", "maur@gmail.com", encoder.encode("miClaveSegura"), null, null, null));
                userService.guardarUsuario(new Usuarios(null, 4L, "riskoder", "risk@gmail.com", encoder.encode("clave123"), null, null, null));
                userService.guardarUsuario(new Usuarios(null, 5L, "Anonimus", "pedroramirez@example.com", encoder.encode("pass5678"), null, null, null));

                System.out.println("Usuarios iniciales insertados correctamente con contraseñas encriptadas.");
            }
        };
    }
}
