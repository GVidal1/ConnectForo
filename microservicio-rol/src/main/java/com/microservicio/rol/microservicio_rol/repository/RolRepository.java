package com.microservicio.rol.microservicio_rol.repository;

import com.microservicio.rol.microservicio_rol.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    List<Rol> findByIdUsuario(Long idUsuario);
}
