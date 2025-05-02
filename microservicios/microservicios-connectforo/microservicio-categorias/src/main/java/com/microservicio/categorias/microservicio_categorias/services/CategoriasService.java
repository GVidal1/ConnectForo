package com.microservicio.categorias.microservicio_categorias.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicio.categorias.microservicio_categorias.client.ForosClient;
import com.microservicio.categorias.microservicio_categorias.dto.ForosDTO;
import com.microservicio.categorias.microservicio_categorias.http.response.ForosPorCategoriasResponse;
import com.microservicio.categorias.microservicio_categorias.model.Categorias;
import com.microservicio.categorias.microservicio_categorias.repository.CategoriasRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoriasService {

  @Autowired
  private CategoriasRepository categoriasRepository;

  @Autowired
  private ForosClient forosClient;

  public List<Categorias> listarCategorias() {
    return categoriasRepository.findAll();
  }

  public Categorias buscarCategoria(Long idCategoria ) {
    return categoriasRepository.findById(idCategoria).orElseThrow(() -> new RuntimeException("Categoria no encontrada en la base de datos"));
  }

  public Categorias guardarCategoria(Categorias categoria) {
    categoriasRepository.save(categoria);
    return categoria;
  }

  public String borrarCategoria(Long idCategoria) {
    categoriasRepository.deleteById(idCategoria);

    return "Categoria Eliminada";
  }

  public Categorias actualizarCategorias(Long id, Categorias categoriaActualizada) {
    Categorias categoriaActual = categoriasRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

    if(categoriaActualizada.getTitulo() != null) {
        categoriaActual.setTitulo(categoriaActualizada.getTitulo());
    }
    if(categoriaActualizada.getDescripcion() != null) {
        categoriaActual.setDescripcion(categoriaActualizada.getDescripcion());
    }

    return categoriasRepository.save(categoriaActual);
  }

  public ForosPorCategoriasResponse buscarForosPorCategorias(Long idCategoria) {

    Categorias categoria = buscarCategoria(idCategoria);

    List<ForosDTO> listaForosDTO = forosClient.listarForosPorCategoria(idCategoria);

    return ForosPorCategoriasResponse.builder()
      .titulo(categoria.getTitulo())
      .descripcion(categoria.getDescripcion())
      .fechaCreacion(categoria.getFechaCreacion())
      .foros(listaForosDTO)
      .build();
  }

  public List<ForosPorCategoriasResponse> obtenerCategoriasConForos() {
    List<Categorias> categorias = categoriasRepository.findAll();

    return categorias.stream()
            .map(categoria -> {
                List<ForosDTO> foros = forosClient.listarForosPorCategoria(categoria.getId());
                return new ForosPorCategoriasResponse(
                    categoria.getId(),
                    categoria.getTitulo(),
                    categoria.getDescripcion(),
                    categoria.getFechaCreacion(),
                    foros
                );
            })
            .toList();
  } 
    
}
