package com.microservicio.soporte.service;

import com.microservicio.soporte.model.Ticket;
import java.util.List;

public interface TicketService {
    Ticket save(Ticket ticket);
    Ticket findById(Long id);
    List<Ticket> findAll();
    List<Ticket> findByUsuarioId(Long usuarioId);
    List<Ticket> findByModeradorId(Long moderadorId);
    List<Ticket> findByEstado(Ticket.EstadoTicket estado);
    List<Ticket> findByCategoria(Ticket.CategoriaTicket categoria);
    Ticket update(Long id, Ticket ticket);
    void delete(Long id);
    Ticket asignarModerador(Long ticketId, Long moderadorId);
    Ticket cambiarEstado(Long ticketId, Ticket.EstadoTicket estado);
} 