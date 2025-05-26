package com.microservicio.categorias.microservicio_categorias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservicio.categorias.microservicio_categorias.model.Categorias;

@Repository
public interface CategoriasRepository extends JpaRepository<Categorias, Long>{

}
