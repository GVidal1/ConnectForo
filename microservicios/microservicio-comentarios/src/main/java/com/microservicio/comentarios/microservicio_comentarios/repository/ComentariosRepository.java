package com.microservicio.comentarios.microservicio_comentarios.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.microservicio.comentarios.microservicio_comentarios.model.Comentarios;

@Repository
public interface ComentariosRepository extends JpaRepository<Comentarios, Long> {
    // Buscar comentarios por palabra en el contenido
    List<Comentarios> findByContenidoContainingIgnoreCase(String palabra);
    
    // Buscar comentarios por ID de usuario
    List<Comentarios> findByIdUsuario(Long idUsuario);
    
    // Buscar comentarios por ID de post
    List<Comentarios> findByIdPost(Long idPost);
    
    // Buscar comentarios por rango de fechas
    List<Comentarios> findByFechaCreacionBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    // Buscar comentarios creados después de una fecha
    List<Comentarios> findByFechaCreacionAfter(LocalDateTime fecha);
    
    // Buscar comentarios creados antes de una fecha
    List<Comentarios> findByFechaCreacionBefore(LocalDateTime fecha);
    
    // Buscar comentarios por longitud del contenido
    @Query("SELECT c FROM Comentarios c WHERE LENGTH(c.contenido) > ?1")
    List<Comentarios> findByLongitudContenidoMayorA(int longitud);
    
    // Buscar comentarios por usuario y post
    List<Comentarios> findByIdUsuarioAndIdPost(Long idUsuario, Long idPost);
    
    // Contar comentarios por post
    Long countByIdPost(Long idPost);
    
    // Contar comentarios por usuario
    Long countByIdUsuario(Long idUsuario);
}
