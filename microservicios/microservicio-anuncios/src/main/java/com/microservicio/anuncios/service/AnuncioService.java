package com.microservicio.anuncios.service;

import com.microservicio.anuncios.exception.ResourceNotFoundException;
import com.microservicio.anuncios.model.Anuncio;
import com.microservicio.anuncios.repository.AnuncioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
} 