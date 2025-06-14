package com.microservicio.post.microservicio_post.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.microservicio.post.microservicio_post.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // Buscar posts por palabra en el título
    List<Post> findByTituloContainingIgnoreCase(String palabra);
    
    // Buscar posts por palabra en el contenido
    List<Post> findByContenidoContainingIgnoreCase(String palabra);
    
    // Buscar posts por ID de usuario
    List<Post> findByIdUsuario(Long idUsuario);
    
    // Buscar posts por ID de foro
    List<Post> findByIdForo(Long idForo);
    
    // Buscar posts por rango de fechas
    List<Post> findByFechaCreacionBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    // Buscar posts creados después de una fecha
    List<Post> findByFechaCreacionAfter(LocalDateTime fecha);
    
    // Buscar posts creados antes de una fecha
    List<Post> findByFechaCreacionBefore(LocalDateTime fecha);
    
    // Buscar posts por longitud del título
    @Query("SELECT p FROM Post p WHERE LENGTH(p.titulo) > ?1")
    List<Post> findByLongitudTituloMayorA(int longitud);
    
    // Buscar posts por usuario y foro
    List<Post> findByIdUsuarioAndIdForo(Long idUsuario, Long idForo);
}
