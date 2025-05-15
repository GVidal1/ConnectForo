package com.microservicio.anuncios.repository;

import com.microservicio.anuncios.model.Anuncio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {
    List<Anuncio> findByForoId(Long foroId);
    List<Anuncio> findByPublicacionId(Long publicacionId);
} 