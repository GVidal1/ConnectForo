package com.microservicio.anuncios.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.microservicio.anuncios.model.Anuncio;
import com.microservicio.anuncios.repository.AnuncioRepository;

@Configuration
public class LoadDataBase {

    @Bean
    CommandLineRunner initDataBase(AnuncioRepository anuncioRepository) {
        return args -> {
            if (anuncioRepository.count() == 0) {
                // Crear anuncios de ejemplo
                Anuncio anuncio1 = new Anuncio();
                anuncio1.setMensaje("Bienvenidos al foro de programación");
                anuncio1.setModeradorId(1L);
                anuncio1.setForoId(1L);
                anuncioRepository.save(anuncio1);

                Anuncio anuncio2 = new Anuncio();
                anuncio2.setMensaje("Reglas del foro: Sé respetuoso con los demás");
                anuncio2.setModeradorId(1L);
                anuncio2.setForoId(1L);
                anuncioRepository.save(anuncio2);

                System.out.println("Datos iniciales de anuncios cargados");
            }
        };
    }
} 