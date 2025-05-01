package com.microservicio.foros.microservicio_foros.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicio.foros.microservicio_foros.Model.Foros;
import com.microservicio.foros.microservicio_foros.Repository.ForosRepository;

@Service
public class ForosService {

  @Autowired
  private ForosRepository forosRepository;

  public List<Foros> listarForos() {
    return forosRepository.findAll();
  }

  public Foros buscarForos(Long id) {
    return forosRepository.findById(id).orElseThrow(() -> new RuntimeException("Foro no encontrado en la base de datos."));
  }

  public Foros guardarForo(Foros foro) {
    forosRepository.save(foro);
    return foro;
  }
  public String borrarForo(Long id) { 
    forosRepository.deleteById(id);
    return "Foro Eliminado correctamente";
  }

  public Foros actualizarForo(Long id, Foros foroActualizado) {
    Foros foroActual = buscarForos(id);

    if (foroActualizado.getTitulo() != null) {
      foroActual.setTitulo(foroActualizado.getTitulo());
    }

    if (foroActualizado.getDescripcion() != null) {
      foroActual.setDescripcion(foroActualizado.getDescripcion());
    }

    if (foroActualizado.getIdCategoria() != null) {
      foroActual.setIdCategoria(foroActualizado.getIdCategoria());
    }

    return forosRepository.save(foroActual);
  }

  public List<Foros> buscarForoPorIdCategoria(Long id) {
    return forosRepository.encontrarForosPorIdCategoria(id);
  }
}
