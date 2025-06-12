package com.example.USER.config;

import com.example.USER.model.Usuarios;
import com.example.USER.repository.UsuarioRepository;
import com.example.USER.service.UserService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class CargarDatos {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            );
        return http.build();
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

                System.out.println("✅ Usuarios iniciales insertados correctamente con contraseñas encriptadas.");
            }
        };
    }
}
