package com.microservicio.soporte.service;

import com.microservicio.soporte.exception.ResourceNotFoundException;
import com.microservicio.soporte.model.Respuesta;
import com.microservicio.soporte.repository.RespuestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RespuestaService {
    @Autowired
    private RespuestaRepository respuestaRepository;

    public Respuesta save(Respuesta respuesta) {
        return respuestaRepository.save(respuesta);
    }

    public Respuesta findById(Long id) {
        return respuestaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Respuesta", "id", id));
    }

    public List<Respuesta> findAll() {
        return respuestaRepository.findAll();
    }

    public List<Respuesta> findByTicketId(Long ticketId) {
        return respuestaRepository.findByTicketId(ticketId);
    }

    public List<Respuesta> findByUsuarioId(Long usuarioId) {
        return respuestaRepository.findByUsuarioId(usuarioId);
    }

    public Respuesta update(Long id, Respuesta respuesta) {
        Respuesta existingRespuesta = findById(id);
        existingRespuesta.setContenido(respuesta.getContenido());
        return respuestaRepository.save(existingRespuesta);
    }

    public void delete(Long id) {
        Respuesta respuesta = findById(id);
        respuestaRepository.delete(respuesta);
    }
} 