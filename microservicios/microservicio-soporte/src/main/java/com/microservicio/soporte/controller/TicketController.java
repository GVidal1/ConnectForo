package com.microservicio.soporte.controller;

import com.microservicio.soporte.model.Ticket;
import com.microservicio.soporte.service.TicketService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@Valid @RequestBody Ticket ticket) {
        Ticket savedTicket = ticketService.save(ticket);
        return new ResponseEntity<>(savedTicket, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.ok(ticketService.findAll());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Ticket>> getTicketsByUsuarioId(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(ticketService.findByUsuarioId(usuarioId));
    }

    @GetMapping("/moderador/{moderadorId}")
    public ResponseEntity<List<Ticket>> getTicketsByModeradorId(@PathVariable Long moderadorId) {
        return ResponseEntity.ok(ticketService.findByModeradorId(moderadorId));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Ticket>> getTicketsByEstado(@PathVariable Ticket.EstadoTicket estado) {
        return ResponseEntity.ok(ticketService.findByEstado(estado));
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Ticket>> getTicketsByCategoria(@PathVariable Ticket.CategoriaTicket categoria) {
        return ResponseEntity.ok(ticketService.findByCategoria(categoria));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable Long id, @Valid @RequestBody Ticket ticket) {
        return ResponseEntity.ok(ticketService.update(id, ticket));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{ticketId}/asignar/{moderadorId}")
    public ResponseEntity<Ticket> asignarModerador(@PathVariable Long ticketId, @PathVariable Long moderadorId) {
        return ResponseEntity.ok(ticketService.asignarModerador(ticketId, moderadorId));
    }

    @PutMapping("/{ticketId}/estado/{estado}")
    public ResponseEntity<Ticket> cambiarEstado(@PathVariable Long ticketId, @PathVariable Ticket.EstadoTicket estado) {
        return ResponseEntity.ok(ticketService.cambiarEstado(ticketId, estado));
    }
} 