package com.microservicio.roles.microservicio_roles.repository;

import com.microservicio.roles.microservicio_roles.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
} 