package com.microservicio.soporte.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.microservicio.soporte.model.Ticket;
import com.microservicio.soporte.model.Ticket.CategoriaTicket;
import com.microservicio.soporte.model.Ticket.EstadoTicket;
import com.microservicio.soporte.repository.TicketRepository;

@Configuration
public class LoadDataBase {

    @Bean
    CommandLineRunner initDataBase(TicketRepository ticketRepository) {
        return args -> {
            if (ticketRepository.count() == 0) {
                // Crear tickets de ejemplo
                Ticket ticket1 = new Ticket();
                ticket1.setTitulo("Problema con la cuenta");
                ticket1.setDescripcion("No puedo acceder a mi cuenta desde ayer");
                ticket1.setUsuarioId(1L);
                ticket1.setCategoria(CategoriaTicket.CUENTA);
                ticket1.setEstado(EstadoTicket.ABIERTO);
                ticketRepository.save(ticket1);

                Ticket ticket2 = new Ticket();
                ticket2.setTitulo("Contenido inapropiado");
                ticket2.setDescripcion("Hay contenido inapropiado en el foro de programación");
                ticket2.setUsuarioId(2L);
                ticket2.setCategoria(CategoriaTicket.CONTENIDO);
                ticket2.setEstado(EstadoTicket.EN_PROCESO);
                ticket2.setModeradorId(1L);
                ticketRepository.save(ticket2);

                System.out.println("Datos iniciales de tickets cargados");
            }
        };
    }
} 