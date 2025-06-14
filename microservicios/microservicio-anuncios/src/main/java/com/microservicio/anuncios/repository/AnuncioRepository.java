package com.microservicio.anuncios.repository;

import com.microservicio.anuncios.model.Anuncio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {
    List<Anuncio> findByForoId(Long foroId);
    List<Anuncio> findByPublicacionId(Long publicacionId);
    
    List<Anuncio> findByModeradorId(Long moderadorId);
    
    List<Anuncio> findByMensajeContainingIgnoreCase(String palabra);
    
    List<Anuncio> findByFechaCreacionBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    List<Anuncio> findByFechaCreacionAfter(LocalDateTime fecha);
    
    List<Anuncio> findByFechaCreacionBefore(LocalDateTime fecha);
    
    @Query("SELECT a FROM Anuncio a WHERE LENGTH(a.mensaje) > ?1")
    List<Anuncio> findByLongitudMensajeMayorA(int longitud);
    
    List<Anuncio> findByModeradorIdAndForoId(Long moderadorId, Long foroId);
    
    List<Anuncio> findByModeradorIdAndPublicacionId(Long moderadorId, Long publicacionId);
    
    Long countByModeradorId(Long moderadorId);
    
    Long countByForoId(Long foroId);
    
    Long countByPublicacionId(Long publicacionId);
} 