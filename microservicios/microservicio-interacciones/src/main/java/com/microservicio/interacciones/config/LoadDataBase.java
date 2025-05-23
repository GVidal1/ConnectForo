package com.microservicio.interacciones.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.microservicio.interacciones.model.Interaccion;
import com.microservicio.interacciones.repository.InteraccionRepository;

@Configuration
public class LoadDataBase {

    @Bean
    CommandLineRunner initDataBase(InteraccionRepository interaccionRepository) {
        return args -> {
            if (interaccionRepository.count() == 0) {
                // Crear interacciones de ejemplo
                Interaccion interaccion1 = new Interaccion();
                interaccion1.setTipo(Interaccion.TipoInteraccion.LIKE);
                interaccion1.setUsuarioId(1L);
                interaccion1.setPublicacionId(1L);
                interaccionRepository.save(interaccion1);

                Interaccion interaccion2 = new Interaccion();
                interaccion2.setTipo(Interaccion.TipoInteraccion.DISLIKE);
                interaccion2.setUsuarioId(2L);
                interaccion2.setPublicacionId(1L);
                interaccionRepository.save(interaccion2);

                System.out.println("Datos iniciales de interacciones cargados");
            }
        };
    }
} 