package com.microservicio.soporte.service;

import com.microservicio.soporte.exception.ResourceNotFoundException;
import com.microservicio.soporte.model.Ticket;
import com.microservicio.soporte.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Ticket findById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket", "id", id));
    }

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public List<Ticket> findByUsuarioId(Long usuarioId) {
        return ticketRepository.findByUsuarioId(usuarioId);
    }

    public List<Ticket> findByModeradorId(Long moderadorId) {
        return ticketRepository.findByModeradorId(moderadorId);
    }

    public List<Ticket> findByEstado(Ticket.EstadoTicket estado) {
        return ticketRepository.findByEstado(estado);
    }

    public List<Ticket> findByCategoria(Ticket.CategoriaTicket categoria) {
        return ticketRepository.findByCategoria(categoria);
    }

    public Ticket update(Long id, Ticket ticket) {
        Ticket existingTicket = findById(id);
        existingTicket.setTitulo(ticket.getTitulo());
        existingTicket.setDescripcion(ticket.getDescripcion());
        existingTicket.setCategoria(ticket.getCategoria());
        existingTicket.setEstado(ticket.getEstado());
        return ticketRepository.save(existingTicket);
    }

    public void delete(Long id) {
        Ticket ticket = findById(id);
        ticketRepository.delete(ticket);
    }

    public Ticket asignarModerador(Long ticketId, Long moderadorId) {
        Ticket ticket = findById(ticketId);
        ticket.setModeradorId(moderadorId);
        ticket.setEstado(Ticket.EstadoTicket.EN_PROCESO);
        return ticketRepository.save(ticket);
    }

    public Ticket cambiarEstado(Long ticketId, Ticket.EstadoTicket estado) {
        Ticket ticket = findById(ticketId);
        ticket.setEstado(estado);
        return ticketRepository.save(ticket);
    }
} 