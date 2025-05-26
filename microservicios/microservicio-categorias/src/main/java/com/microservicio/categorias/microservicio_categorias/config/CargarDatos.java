package com.microservicio.categorias.microservicio_categorias.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.microservicio.categorias.microservicio_categorias.model.Categorias;
import com.microservicio.categorias.microservicio_categorias.repository.CategoriasRepository;

@Configuration
public class CargarDatos {

    @Bean
    CommandLineRunner initDataBase(CategoriasRepository categoriasRepository) {
        return args -> {
            // Solo se ejecuta si no hay categorías en la base de datos
            if (categoriasRepository.count() == 0) {
                // Insertamos las categorías en la base de datos sin el builder
                categoriasRepository.save(new Categorias(null, "Frontend", "Desarrollo de interfaces de usuario", null));
                categoriasRepository.save(new Categorias(null, "Backend", "Lógica del servidor y APIs", null));
                categoriasRepository.save(new Categorias(null, "Mobile", "Desarrollo para dispositivos móviles", null));
                categoriasRepository.save(new Categorias(null, "Testing", "Aseguramiento de calidad", null));
                categoriasRepository.save(new Categorias(null, "UI/UX", "Diseño de experiencia de usuario", null));

                System.out.println("Datos de categorías cargados correctamente.");
            }
        };
    }
}
