package com.microservicio.foros.microservicio_foros.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicio.foros.microservicio_foros.Model.Foros;
import com.microservicio.foros.microservicio_foros.Repository.ForosRepository;
import com.microservicio.foros.microservicio_foros.client.CategoriaClient;
import com.microservicio.foros.microservicio_foros.client.UsuarioClient;

@Service
public class ForosService {

  @Autowired
  private ForosRepository forosRepository;

  @Autowired
  private CategoriaClient categoriaClient;

  @Autowired
  private UsuarioClient usuarioClient;

  public List<Foros> listarForos() {
    return forosRepository.findAll();
  }

  public Foros buscarForos(Long id) {
    return forosRepository.findById(id).orElseThrow(() -> new RuntimeException("Foro no encontrado en la base de datos."));
  }

  public Foros guardarForo(Foros foro) {
    // Validar que la categoría exista
    Map<String, Object> categorias = categoriaClient.obtenerCategoriaPorId(foro.getIdCategoria());
    if (categorias == null || categorias.isEmpty()) {
      throw new RuntimeException("El id de la categoria no se ha encontrado. No se puede realizar el foro.");
    }

    // Validar que el usuario exista
    Map<String, Object> usuario = usuarioClient.obtenerUsuarioPorId(foro.getIdUsuarioCreador());
    if (usuario == null || usuario.isEmpty()) {
      throw new RuntimeException("El id del usuario creador no se ha encontrado. No se puede realizar el foro.");
    }

    return forosRepository.save(foro);
  }

  public String borrarForo(Long id) { 
    Foros foroAct = buscarForos(id);
    forosRepository.deleteById(foroAct.getId());
    return "Foro Eliminado correctamente";
  }

  public Foros actualizarForo(Long id, Foros foroActualizado) {
    Foros foroActual = buscarForos(id);

    if (foroActualizado.getIdCategoria() != null) {
      Map<String, Object> categorias = categoriaClient.obtenerCategoriaPorId(foroActualizado.getIdCategoria());
      if (categorias == null || categorias.isEmpty()) {
        throw new RuntimeException("El id de la categoria no se ha encontrado. No se puede actualizar el foro.");
      }
    }

    if (foroActualizado.getIdUsuarioCreador() != null) {
      Map<String, Object> usuario = usuarioClient.obtenerUsuarioPorId(foroActualizado.getIdUsuarioCreador());
      if (usuario == null || usuario.isEmpty()) {
        throw new RuntimeException("El id del usuario creador no se ha encontrado. No se puede actualizar el foro.");
      }
    }

    if (foroActualizado.getTitulo() != null) {
      foroActual.setTitulo(foroActualizado.getTitulo());
    }

    if (foroActualizado.getContenido() != null) {
      foroActual.setContenido(foroActualizado.getContenido());
    }

    if (foroActualizado.getIdCategoria() != null) {
      foroActual.setIdCategoria(foroActualizado.getIdCategoria());
    }

    if (foroActualizado.getIdUsuarioCreador() != null) {
      foroActual.setIdUsuarioCreador(foroActualizado.getIdUsuarioCreador());
    }

    return forosRepository.save(foroActual);
  }

  public List<Foros> buscarForosPorUsuario(Long idUsuario) {
    Map<String, Object> usuario = usuarioClient.obtenerUsuarioPorId(idUsuario);
    if (usuario == null || usuario.isEmpty()) {
      throw new RuntimeException("El usuario no existe");
    }
    return forosRepository.encontrarForosPorIdUsuario(idUsuario);
  }

  public List<Foros> buscarForosPorPalabraEnTitulo(String palabra) {
    return forosRepository.encontrarForosPorPalabraEnTitulo(palabra);
  }

  public List<Foros> buscarForosPorPalabraEnContenido(String palabra) {
    return forosRepository.encontrarForosPorPalabraEnContenido(palabra);
  }

  public List<Foros> buscarForosPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
    return forosRepository.encontrarForosPorRangoFechas(fechaInicio, fechaFin);
  }

  public List<Foros> buscarForosPorCategoriaYUsuario(Long idCategoria, Long idUsuario) {
    Map<String, Object> categoria = categoriaClient.obtenerCategoriaPorId(idCategoria);
    if (categoria == null || categoria.isEmpty()) {
      throw new RuntimeException("La categoría no existe");
    }

    Map<String, Object> usuario = usuarioClient.obtenerUsuarioPorId(idUsuario);
    if (usuario == null || usuario.isEmpty()) {
      throw new RuntimeException("El usuario no existe");
    }

    return forosRepository.encontrarForosPorCategoriaYUsuario(idCategoria, idUsuario);
  }

  public List<Foros> buscarForosPorLongitudTitulo(int longitud) {
    return forosRepository.encontrarForosPorLongitudTitulo(longitud);
  }

  public List<Foros> buscarForosCreadosDespuesDe(LocalDateTime fecha) {
    return forosRepository.encontrarForosCreadosDespuesDe(fecha);
  }

  public List<Foros> buscarForosCreadosAntesDe(LocalDateTime fecha) {
    return forosRepository.encontrarForosCreadosAntesDe(fecha);
  }
}
