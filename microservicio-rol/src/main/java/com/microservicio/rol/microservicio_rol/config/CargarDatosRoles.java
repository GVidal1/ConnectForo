package com.microservicio.rol.microservicio_rol.config;

import com.microservicio.rol.microservicio_rol.model.Rol;
import com.microservicio.rol.microservicio_rol.repository.RolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CargarDatosRoles {

    @Bean
    CommandLineRunner initDatabase(RolRepository rolRepository) {
        return args -> {
            if (rolRepository.count() == 0) {
                
                rolRepository.save(new Rol(null, Rol.TipoRol.ADMIN));
                rolRepository.save(new Rol(null,  Rol.TipoRol.MODERADOR));
                rolRepository.save(new Rol(null,  Rol.TipoRol.SOPORTE));
                rolRepository.save(new Rol(null,  Rol.TipoRol.GESTOR_ANUNCIOS));
                rolRepository.save(new Rol(null,  Rol.TipoRol.USUARIO));
                
                System.out.println("Datos de roles cargados .");
            }
        };
    }
}
