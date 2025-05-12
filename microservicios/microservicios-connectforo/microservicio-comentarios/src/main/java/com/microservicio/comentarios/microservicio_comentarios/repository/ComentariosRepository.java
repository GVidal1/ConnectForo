package com.microservicio.comentarios.microservicio_comentarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservicio.comentarios.microservicio_comentarios.model.Comentarios;

@Repository
public interface ComentariosRepository extends JpaRepository<Comentarios, Long> {

}
