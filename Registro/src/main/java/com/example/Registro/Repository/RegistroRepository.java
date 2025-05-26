package com.example.Registro.Repository;

import com.example.Registro.Model.RegistroModel;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroRepository extends JpaRepository<RegistroModel, Long> {
    Optional<RegistroModel> findByNombreUsuario(String nombreUsuario);



}

