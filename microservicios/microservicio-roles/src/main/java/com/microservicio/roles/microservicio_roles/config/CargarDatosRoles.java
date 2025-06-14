package com.microservicio.roles.microservicio_roles.config;

import com.microservicio.roles.microservicio_roles.model.Rol;
import com.microservicio.roles.microservicio_roles.repository.RolRepository;
import com.microservicio.roles.microservicio_roles.service.RolService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CargarDatosRoles {

    @Autowired
    private RolService rolService;

    @Autowired
    private RolRepository rolRepository;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            if (rolRepository.count() == 0) {
                rolService.guardarRol(new Rol(null,"ADMIN"));
                rolService.guardarRol(new Rol(null,"MODERADOR"));
                rolService.guardarRol(new Rol(null,"SOPORTE"));
                rolService.guardarRol(new Rol(null,"GESTOR_ANUNCIOS"));
                rolService.guardarRol(new Rol(null,"USUARIO"));
                
                System.out.println("Datos de roles cargados correctamente.");
            }
        };
    }
} 