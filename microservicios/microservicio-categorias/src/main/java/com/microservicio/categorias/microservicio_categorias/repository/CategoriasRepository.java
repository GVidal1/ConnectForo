package com.microservicio.categorias.microservicio_categorias.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.microservicio.categorias.microservicio_categorias.model.Categorias;

@Repository
public interface CategoriasRepository extends JpaRepository<Categorias, Long>{
    
    List<Categorias> findByTituloContainingIgnoreCase(String titulo);
    
    List<Categorias> findByDescripcionContainingIgnoreCase(String descripcion);
    
    @Query("SELECT c FROM Categorias c WHERE LENGTH(c.titulo) > :longitud")
    List<Categorias> findByTituloLongitudMayorA(int longitud);
    
    @Query("SELECT c FROM Categorias c WHERE c.fechaCreacion >= :fechaInicio AND c.fechaCreacion <= :fechaFin")
    List<Categorias> findByFechaCreacionBetween(java.time.LocalDateTime fechaInicio, java.time.LocalDateTime fechaFin);
}
