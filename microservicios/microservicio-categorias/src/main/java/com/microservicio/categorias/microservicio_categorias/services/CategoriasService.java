package com.microservicio.categorias.microservicio_categorias.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicio.categorias.microservicio_categorias.model.Categorias;
import com.microservicio.categorias.microservicio_categorias.repository.CategoriasRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@Service
@Transactional
public class CategoriasService {

    @Autowired
    private CategoriasRepository categoriasRepository;
    
    private final Validator validator;

    public CategoriasService() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    public List<Categorias> listarCategorias() {
        return categoriasRepository.findAll();
    }

    public Categorias buscarCategoria(Long idCategoria) {
        return categoriasRepository.findById(idCategoria)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada en la base de datos"));
    }

    public Categorias guardarCategoria(Categorias categoria) {
        Set<ConstraintViolation<Categorias>> violations = validator.validate(categoria);
        if (!violations.isEmpty()) {
            throw new RuntimeException(violations.iterator().next().getMessage());
        }
        return categoriasRepository.save(categoria);
    }

    public String borrarCategoria(Long idCategoria) {
        Categorias categoriaActual = buscarCategoria(idCategoria);
        categoriasRepository.deleteById(categoriaActual.getId());
        return "Categoria Eliminada";
    }

    public Categorias actualizarCategorias(Long id, Categorias categoriaActualizada) {
        Categorias categoriaActual = buscarCategoria(id);

        if (categoriaActualizada.getTitulo() != null) {
            categoriaActual.setTitulo(categoriaActualizada.getTitulo());
        }
        if (categoriaActualizada.getDescripcion() != null) {
            categoriaActual.setDescripcion(categoriaActualizada.getDescripcion());
        }
        return guardarCategoria(categoriaActual);
        // return categoriasRepository.save(categoriaActual);
    }

    public List<Categorias> buscarPorTitulo(String titulo) {
        return categoriasRepository.findByTituloContainingIgnoreCase(titulo);
    }

    public List<Categorias> buscarPorDescripcion(String descripcion) {
        return categoriasRepository.findByDescripcionContainingIgnoreCase(descripcion);
    }

    public List<Categorias> buscarPorLongitudTitulo(int longitud) {
        return categoriasRepository.findByTituloLongitudMayorA(longitud);
    }

    public List<Categorias> buscarPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return categoriasRepository.findByFechaCreacionBetween(fechaInicio, fechaFin);
    }
}
