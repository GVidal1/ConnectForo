package com.microservicio.soporte.service;

import com.microservicio.soporte.model.Respuesta;
import java.util.List;

public interface RespuestaService {
    Respuesta save(Respuesta respuesta);
    Respuesta findById(Long id);
    List<Respuesta> findAll();
    List<Respuesta> findByTicketId(Long ticketId);
    List<Respuesta> findByUsuarioId(Long usuarioId);
    Respuesta update(Long id, Respuesta respuesta);
    void delete(Long id);
} 