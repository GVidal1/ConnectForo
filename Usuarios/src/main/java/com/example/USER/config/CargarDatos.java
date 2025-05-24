package com.example.USER.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.USER.model.Usuarios;
import com.example.USER.repository.UsuarioRepository;

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
    CommandLineRunner initDataBase(UsuarioRepository usuariosRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (usuariosRepository.count() == 0) {
                usuariosRepository.save(new Usuarios(null, "gabriel", passwordEncoder.encode("password123"), "gabriel@duocuc.cl", null));
                usuariosRepository.save(new Usuarios(null, "francisco", passwordEncoder.encode("1234secure"), "fran@gmail.com", null));
                usuariosRepository.save(new Usuarios(null, "mauricio", passwordEncoder.encode("miClaveSegura"), "maur@gmail.com", null));
                usuariosRepository.save(new Usuarios(null, "riskoder", passwordEncoder.encode("clave123"), "risk@gmail.com", null));
                usuariosRepository.save(new Usuarios(null, "Anonimus", passwordEncoder.encode("pass5678"), "pedroramirez@example.com", null));

                System.out.println("✅ Datos de usuarios cargados correctamente con contraseñas encriptadas.");
            }
        };
    }
}

