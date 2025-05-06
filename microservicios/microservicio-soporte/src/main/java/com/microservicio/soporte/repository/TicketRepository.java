package com.microservicio.soporte.repository;

import com.microservicio.soporte.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByUsuarioId(Long usuarioId);
    List<Ticket> findByModeradorId(Long moderadorId);
    List<Ticket> findByEstado(Ticket.EstadoTicket estado);
    List<Ticket> findByCategoria(Ticket.CategoriaTicket categoria);
} 