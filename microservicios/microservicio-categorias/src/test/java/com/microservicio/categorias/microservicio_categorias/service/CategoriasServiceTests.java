package com.microservicio.categorias.microservicio_categorias.service;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.microservicio.categorias.microservicio_categorias.model.Categorias;
import com.microservicio.categorias.microservicio_categorias.repository.CategoriasRepository;
import com.microservicio.categorias.microservicio_categorias.services.CategoriasService;

@ExtendWith(MockitoExtension.class)
public class CategoriasServiceTests {

    @Mock
    private CategoriasRepository categoriasRepository;

    @InjectMocks
    private CategoriasService categoriasService;

    private Categorias categoriaTest;
    private List<Categorias> categoriasList;

    // Si el test verifica validaciones → NO necesitas mock del repositorio
    // Si el test verifica operaciones CRUD → SÍ necesitas mock del repositorio
    // Si el test verifica existencia → Solo necesitas mock de findById

    @BeforeEach
    void setUp() {
        categoriaTest = new Categorias();
        categoriaTest.setId(1L);
        categoriaTest.setTitulo("Categoria 1");
        categoriaTest.setDescripcion("Descripcion 1");
        categoriaTest.setFechaCreacion(LocalDateTime.now());
        categoriasList = Arrays.asList(categoriaTest);
    }

    @Test
    @DisplayName("Retornar lista de categorias cuando existan estas")
    void testListarCategorias() {
        when(categoriasRepository.findAll()).thenReturn(categoriasList);

        List<Categorias> result = categoriasService.listarCategorias();

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(categoriaTest.getId());
        assertThat(result.get(0).getTitulo()).isEqualTo(categoriaTest.getTitulo());
        assertThat(result.get(0).getDescripcion()).isEqualTo(categoriaTest.getDescripcion());
    }

    @Test
    @DisplayName("Retornar lista vacia cuando no existan categorias")
    void testListarCategoriasVacias() {
        when(categoriasRepository.findAll()).thenReturn(Collections.emptyList());

        List<Categorias> result = categoriasService.listarCategorias();

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Encontrar categoria cuando el ID existe")
    void testBuscarCategoriaPorIdExistente() {
        Long idExistente = 1L;
        when(categoriasRepository.findById(idExistente))
            .thenReturn(Optional.of(categoriaTest));

        Categorias result = categoriasService.buscarCategoria(idExistente);

        assertThat(result).isEqualTo(categoriaTest);
    }

    // @Test
    // @DisplayName("Lanzar excepcion cuando el ID no existe")
    // void testBuscarCategoriaPorIdInexistente() {
    //     Long idInexistente = 999L;
    //     when(categoriasRepository.findById(idInexistente))
    //         .thenReturn(Optional.empty());

    //     RuntimeException exception = assertThrows(RuntimeException.class, () -> {
    //         categoriasService.buscarCategoria(idInexistente);
    //     });

    //     assertThat(exception.getMessage()).isEqualTo("Categoria no encontrada en la base de datos");
    // }

    // POST - GUARDA MODELO 
    @Test 
    @DisplayName("Guardar Modelo Correcto de Categoria")
    void testGuardarCategoriaCorrecta() {
        when(categoriasRepository.save(categoriaTest)).thenReturn(categoriaTest);

        Categorias result = categoriasService.guardarCategoria(categoriaTest);

        assertThat(result).isEqualTo(categoriaTest);
    }

    @Test
    @DisplayName("Visualizar mensaje cuando se borre una categoria por su ID")
    void testEliminarCategoriaPorId() {
        when(categoriasRepository.findById(categoriaTest.getId())).thenReturn(Optional.of(categoriaTest));
        doNothing().when(categoriasRepository).deleteById(categoriaTest.getId());
        
        String result = categoriasService.borrarCategoria(categoriaTest.getId());
        
        assertThat(result).isEqualTo("Categoria Eliminada");
    }

    @Test
    @DisplayName("Actualizar categoria existente con Modelo Correcto")
    void testActualizarCategoriaPorIdCorrectamente() {
        Long idExistente = 1L;
        when(categoriasRepository.findById(idExistente)).thenReturn(Optional.of(categoriaTest));
        when(categoriasRepository.save(categoriaTest)).thenReturn(categoriaTest);

        Categorias result = categoriasService.actualizarCategorias(idExistente, categoriaTest);

        assertThat(result).isEqualTo(categoriaTest);
    }

    @Test
    @DisplayName("Buscar por titulo cuando el titulo existe en la base de datos")
    void testBuscarPorTitulo_CuandoExiste() {
        String titutlo = "Categoria 1";

        when(categoriasRepository.findByTituloContainingIgnoreCase(titutlo)).thenReturn(categoriasList);

        List<Categorias> result = categoriasService.buscarPorTitulo(titutlo);

        assertThat(result).isEqualTo(categoriasList);
    }

    @Test
    @DisplayName("Buscar por descripción cuando existe en la base de datos")
    void testBuscarPorDescripcion_CuandoExiste() {
        String descripcion = "Descripcion 1";
        when(categoriasRepository.findByDescripcionContainingIgnoreCase(descripcion)).thenReturn(categoriasList);

        List<Categorias> result = categoriasService.buscarPorDescripcion(descripcion);

        assertThat(result).isEqualTo(categoriasList);
    }

    @Test
    @DisplayName("Buscar por longitud de título mayor a un valor")
    void testBuscarPorLongitudTitulo_CuandoExisten() {
        int longitud = 5;
        when(categoriasRepository.findByTituloLongitudMayorA(longitud)).thenReturn(categoriasList);

        List<Categorias> result = categoriasService.buscarPorLongitudTitulo(longitud);

        assertThat(result).isEqualTo(categoriasList);
    }

    @Test
    @DisplayName("Buscar por rango de fechas cuando existen categorías")
    void testBuscarPorRangoFechas_CuandoExisten() {
        LocalDateTime fechaInicio = LocalDateTime.now().minusDays(1);
        LocalDateTime fechaFin = LocalDateTime.now().plusDays(1);
        when(categoriasRepository.findByFechaCreacionBetween(fechaInicio, fechaFin)).thenReturn(categoriasList);

        List<Categorias> result = categoriasService.buscarPorRangoFechas(fechaInicio, fechaFin);

        assertThat(result).isEqualTo(categoriasList);
    }
}
