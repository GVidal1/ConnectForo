package com.example.USER.repository;



import com.example.USER.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
<<<<<<< HEAD
public interface UsuarioRepository extends JpaRepository <Usuarios,Integer> {
=======
public interface UsuarioRepository extends JpaRepository <Usuarios, Long> {

>>>>>>> 0d39f00d78eadc7b2f9f3ad3e912f5053ad9f81d
}
