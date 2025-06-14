package com.microservicio.foros.microservicio_foros.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.microservicio.foros.microservicio_foros.Model.Foros;

@Repository
public interface ForosRepository extends JpaRepository<Foros, Long>{

  @Query("SELECT f FROM Foros f WHERE f.idCategoria = :idCategoria")
  List<Foros> encontrarForosPorIdCategoria(Long idCategoria);

  @Query("SELECT f FROM Foros f WHERE f.idUsuarioCreador = :idUsuario")
  List<Foros> encontrarForosPorIdUsuario(Long idUsuario);

  @Query("SELECT f FROM Foros f WHERE f.titulo LIKE %:palabra%")
  List<Foros> encontrarForosPorPalabraEnTitulo(String palabra);

  @Query("SELECT f FROM Foros f WHERE f.contenido LIKE %:palabra%")
  List<Foros> encontrarForosPorPalabraEnContenido(String palabra);

  @Query("SELECT f FROM Foros f WHERE f.fechaCreacion >= :fechaInicio AND f.fechaCreacion <= :fechaFin")
  List<Foros> encontrarForosPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin);

  @Query("SELECT f FROM Foros f WHERE f.idCategoria = :idCategoria AND f.idUsuarioCreador = :idUsuario")
  List<Foros> encontrarForosPorCategoriaYUsuario(Long idCategoria, Long idUsuario);

  @Query("SELECT f FROM Foros f WHERE LENGTH(f.titulo) > :longitud")
  List<Foros> encontrarForosPorLongitudTitulo(int longitud);

  @Query("SELECT f FROM Foros f WHERE f.fechaCreacion >= :fecha")
  List<Foros> encontrarForosCreadosDespuesDe(LocalDateTime fecha);

  @Query("SELECT f FROM Foros f WHERE f.fechaCreacion <= :fecha")
  List<Foros> encontrarForosCreadosAntesDe(LocalDateTime fecha);
}
