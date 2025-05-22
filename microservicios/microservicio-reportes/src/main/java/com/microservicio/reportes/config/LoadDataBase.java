package com.microservicio.reportes.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.microservicio.reportes.model.Reporte;
import com.microservicio.reportes.repository.ReporteRepository;

@Configuration
public class LoadDataBase {

    @Bean
    CommandLineRunner initDataBase(ReporteRepository reporteRepository) {
        return args -> {
            if (reporteRepository.count() == 0) {
                // Crear reportes de ejemplo
                Reporte reporte1 = new Reporte();
                reporte1.setTitulo("Contenido inapropiado");
                reporte1.setDescripcion("Se ha detectado contenido inapropiado en una publicación");
                reporte1.setTipoReporte("CONTENIDO");
                reporte1.setIdUsuarioReportante(1L);
                reporte1.setIdUsuarioReportado(2L);
                reporte1.setIdForo(1L);
                reporteRepository.save(reporte1);

                Reporte reporte2 = new Reporte();
                reporte2.setTitulo("Spam en comentarios");
                reporte2.setDescripcion("Usuario realizando spam en comentarios");
                reporte2.setTipoReporte("SPAM");
                reporte2.setIdUsuarioReportante(2L);
                reporte2.setIdUsuarioReportado(3L);
                reporte2.setIdForo(1L);
                reporteRepository.save(reporte2);

                System.out.println("Datos iniciales de reportes cargados");
            }
        };
    }
} 