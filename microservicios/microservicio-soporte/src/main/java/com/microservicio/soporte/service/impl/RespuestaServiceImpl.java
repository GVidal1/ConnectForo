package com.microservicio.soporte.service.impl;

import com.microservicio.soporte.exception.ResourceNotFoundException;
import com.microservicio.soporte.model.Respuesta;
import com.microservicio.soporte.repository.RespuestaRepository;
import com.microservicio.soporte.service.RespuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RespuestaServiceImpl implements RespuestaService {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Override
    public Respuesta save(Respuesta respuesta) {
        return respuestaRepository.save(respuesta);
    }

    @Override
    public Respuesta findById(Long id) {
        return respuestaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Respuesta", "id", id));
    }

    @Override
    public List<Respuesta> findAll() {
        return respuestaRepository.findAll();
    }

    @Override
    public List<Respuesta> findByTicketId(Long ticketId) {
        return respuestaRepository.findByTicketId(ticketId);
    }

    @Override
    public List<Respuesta> findByUsuarioId(Long usuarioId) {
        return respuestaRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public Respuesta update(Long id, Respuesta respuesta) {
        Respuesta existingRespuesta = findById(id);
        existingRespuesta.setContenido(respuesta.getContenido());
        return respuestaRepository.save(existingRespuesta);
    }

    @Override
    public void delete(Long id) {
        Respuesta respuesta = findById(id);
        respuestaRepository.delete(respuesta);
    }
} 