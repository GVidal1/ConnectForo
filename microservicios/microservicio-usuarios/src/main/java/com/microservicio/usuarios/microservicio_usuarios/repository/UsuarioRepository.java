package com.microservicio.usuarios.microservicio_usuarios.repository;

import com.microservicio.usuarios.microservicio_usuarios.model.Usuarios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {
    Optional<Usuarios> findByCorreo(String correo);
    List<Usuarios> findByIdRol(Long idRol);
} 