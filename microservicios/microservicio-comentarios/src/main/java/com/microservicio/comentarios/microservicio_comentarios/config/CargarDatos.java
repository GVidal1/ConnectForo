package com.microservicio.comentarios.microservicio_comentarios.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.microservicio.comentarios.microservicio_comentarios.model.Comentarios;
import com.microservicio.comentarios.microservicio_comentarios.repository.ComentariosRepository;

@Configuration
public class CargarDatos {

    @Bean
    CommandLineRunner initDataBase(ComentariosRepository comentariosRepository) {
        return args -> {
            // Solo se ejecuta si no hay comentarios en la base de datos
            if (comentariosRepository.count() == 0) {
                // Insertamos los comentarios de ejemplo en la base de datos
                comentariosRepository.save(new Comentarios(null, "Este es un comentario sobre el Frontend", 1L, 1L, null));
                comentariosRepository.save(new Comentarios(null, "Comentario sobre Backend y APIs", 2L, 1L, null));
                comentariosRepository.save(new Comentarios(null, "Comentario sobre desarrollo móvil", 3L, 2L, null));
                comentariosRepository.save(new Comentarios(null, "Comentario acerca de pruebas de calidad", 4L, 2L, null));
                comentariosRepository.save(new Comentarios(null, "Comentario sobre UI/UX y diseño de interfaces", 5L, 3L, null));

                System.out.println("Datos de comentarios cargados correctamente.");
            }
        };
    }
}
