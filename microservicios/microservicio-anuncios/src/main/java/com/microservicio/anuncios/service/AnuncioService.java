package com.microservicio.anuncios.service;

import com.microservicio.anuncios.exception.ResourceNotFoundException;
import com.microservicio.anuncios.model.Anuncio;
import com.microservicio.anuncios.repository.AnuncioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnuncioService {
    @Autowired
    private AnuncioRepository anuncioRepository;

    public List<Anuncio> findAll() {
        return anuncioRepository.findAll();
    }

    public Anuncio findById(Long id) {
        return anuncioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Anuncio", "id", id));
    }

    public Anuncio save(Anuncio anuncio) {
        return anuncioRepository.save(anuncio);
    }

    public void deleteById(Long id) {
        if (!anuncioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Anuncio", "id", id);
        }
        anuncioRepository.deleteById(id);
    }

    public List<Anuncio> findByForoId(Long foroId) {
        return anuncioRepository.findByForoId(foroId);
    }

    public List<Anuncio> findByPublicacionId(Long publicacionId) {
        return anuncioRepository.findByPublicacionId(publicacionId);
    }

    public List<Anuncio> findByModeradorId(Long moderadorId) {
        return anuncioRepository.findByModeradorId(moderadorId);
    }

    public List<Anuncio> findByPalabraEnMensaje(String palabra) {
        return anuncioRepository.findByMensajeContainingIgnoreCase(palabra);
    }

    public List<Anuncio> findByRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        if (fechaInicio.isAfter(fechaFin)) {
            throw new RuntimeException("La fecha de inicio debe ser anterior a la fecha fin");
        }
        return anuncioRepository.findByFechaCreacionBetween(fechaInicio, fechaFin);
    }

    public List<Anuncio> findCreadosDespuesDe(LocalDateTime fecha) {
        return anuncioRepository.findByFechaCreacionAfter(fecha);
    }

    public List<Anuncio> findCreadosAntesDe(LocalDateTime fecha) {
        return anuncioRepository.findByFechaCreacionBefore(fecha);
    }

    public List<Anuncio> findByLongitudMensaje(int longitud) {
        return anuncioRepository.findByLongitudMensajeMayorA(longitud);
    }

    public List<Anuncio> findByModeradorYForo(Long moderadorId, Long foroId) {
        return anuncioRepository.findByModeradorIdAndForoId(moderadorId, foroId);
    }

    public List<Anuncio> findByModeradorYPublicacion(Long moderadorId, Long publicacionId) {
        return anuncioRepository.findByModeradorIdAndPublicacionId(moderadorId, publicacionId);
    }

    public Long contarPorModerador(Long moderadorId) {
        return anuncioRepository.countByModeradorId(moderadorId);
    }

    public Long contarPorForo(Long foroId) {
        return anuncioRepository.countByForoId(foroId);
    }

    public Long contarPorPublicacion(Long publicacionId) {
        return anuncioRepository.countByPublicacionId(publicacionId);
    }
} 