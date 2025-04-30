package com.microservicio.categorias.microservicio_categorias.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicio.categorias.microservicio_categorias.model.Categorias;
import com.microservicio.categorias.microservicio_categorias.repository.CategoriasRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoriasService {

  @Autowired
  private CategoriasRepository categoriasRepository;

  public List<Categorias> listarCategorias() {
    return categoriasRepository.findAll();
  }

  public Categorias buscarCategoria(Long idCategoria ) {
    return categoriasRepository.findById(idCategoria).orElseThrow(() -> new RuntimeException("Categoria no encontrada en la base de datos"));
  }

  public String guardarCategoria(Categorias categoria) {
    categoriasRepository.save(categoria);
    return "Categoria guardada correctamente";
  }

  public String borrarCategoria(Categorias categoria) {
    categoriasRepository.delete(categoria);

    return "Categoria eliminada correctamente";
  }

  public Categorias actualizarCategorias(Categorias categorias) {
    Categorias categoriaActual = categoriasRepository.findById(categorias.getId()).orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

    categoriaActual.setTitulo(categorias.getTitulo());
    categoriaActual.setDescripcion(categorias.getDescripcion());

    return categoriasRepository.save(categoriaActual);
  }
}
