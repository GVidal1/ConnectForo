package com.microservicio.soporte.service;

import com.microservicio.soporte.exception.ResourceNotFoundException;
import com.microservicio.soporte.client.UsuarioClient;
import com.microservicio.soporte.model.Ticket;
import com.microservicio.soporte.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    @Transactional(readOnly = true)
    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Ticket findById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket", "id", id));
    }

    @Transactional
    public Ticket save(Ticket ticket) {
        // Validar usuario si existe
        try {
            usuarioClient.getUsuarioById(ticket.getUsuarioId());
        } catch (Exception e) {
            throw new ResourceNotFoundException("Usuario", "id", ticket.getUsuarioId());
        }

        // Validar moderador si existe
        if (ticket.getModeradorId() != null) {
            try {
                usuarioClient.getUsuarioById(ticket.getModeradorId());
            } catch (Exception e) {
                throw new ResourceNotFoundException("Moderador", "id", ticket.getModeradorId());
            }
        }

        return ticketRepository.save(ticket);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!ticketRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ticket", "id", id);
        }
        ticketRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Ticket> findByUsuarioId(Long usuarioId) {
        return ticketRepository.findByUsuarioId(usuarioId);
    }

    @Transactional(readOnly = true)
    public List<Ticket> findByModeradorId(Long moderadorId) {
        return ticketRepository.findByModeradorId(moderadorId);
    }

    @Transactional(readOnly = true)
    public List<Ticket> findByEstado(Ticket.EstadoTicket estado) {
        return ticketRepository.findByEstado(estado);
    }

    @Transactional(readOnly = true)
    public List<Ticket> findByCategoria(Ticket.CategoriaTicket categoria) {
        return ticketRepository.findByCategoria(categoria);
    }

    @Transactional
    public Ticket updateEstado(Long id, Ticket.EstadoTicket nuevoEstado) {
        Ticket ticket = findById(id);
        ticket.setEstado(nuevoEstado);
        return ticketRepository.save(ticket);
    }

    @Transactional
    public Ticket update(Long id, Ticket ticket) {
        Ticket existingTicket = findById(id);
        existingTicket.setTitulo(ticket.getTitulo());
        existingTicket.setDescripcion(ticket.getDescripcion());
        existingTicket.setCategoria(ticket.getCategoria());
        existingTicket.setEstado(ticket.getEstado());
        return ticketRepository.save(existingTicket);
    }

    @Transactional
    public Ticket asignarModerador(Long ticketId, Long moderadorId) {
        Ticket ticket = findById(ticketId);
        ticket.setModeradorId(moderadorId);
        ticket.setEstado(Ticket.EstadoTicket.EN_PROCESO);
        return ticketRepository.save(ticket);
    }

    @Transactional
    public Ticket cambiarEstado(Long ticketId, Ticket.EstadoTicket estado) {
        Ticket ticket = findById(ticketId);
        ticket.setEstado(estado);
        return ticketRepository.save(ticket);
    }
} 