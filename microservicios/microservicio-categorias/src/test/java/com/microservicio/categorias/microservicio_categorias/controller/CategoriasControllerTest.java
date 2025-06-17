package com.microservicio.categorias.microservicio_categorias.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicio.categorias.microservicio_categorias.model.Categorias;
import com.microservicio.categorias.microservicio_categorias.services.CategoriasService;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CategoriasController.class)
public class CategoriasControllerTest {

  @MockBean
  private CategoriasService service;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  //Inicializar variables globales
  private Categorias categoriaTest;
  private List<Categorias> listaCateriaTest;

  @BeforeEach
  void setUp() {
    categoriaTest = new Categorias(1L, "Categoria Test", "testing de controllador", LocalDateTime.now());

    listaCateriaTest = Arrays.asList(categoriaTest);
  }
  

  //Test de GET - /api/categorias
  @Test
  void testObtenerCategorias_ConContenido() throws Exception {

    when(service.listarCategorias()).thenReturn(listaCateriaTest);

    mockMvc.perform(get("/api/categorias"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$[0].id").value(1));
  }

  @Test
  void testObetnerCategorias_SinContenido() throws Exception {
    when(service.listarCategorias()).thenReturn(List.of());

    mockMvc.perform(get("/api/categorias"))
            .andExpect(status().isNoContent());
  }
  
  //POST - /api/categorias
  @Test 
  void testCrearCategoria_Existoso() throws Exception {
    when(service.guardarCategoria(categoriaTest)).thenReturn(categoriaTest);
    //ContentType y Content van dentro del Post
    mockMvc.perform(post("/api/categorias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(categoriaTest)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.titulo").value("Categoria Test"));
  }

  @Test
  void testCrearCategoria_Erronea() throws Exception {
    Categorias categoriaErronea = new Categorias();
    categoriaErronea.setTitulo("");
    categoriaErronea.setDescripcion("");

    mockMvc.perform(post("/api/categorias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(categoriaErronea)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors[0]").value("titulo: El titúlo no puede estar vacío"))
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.timestamp").exists());
  }

  // GET - /api/categorias/{idCategoria}
  @Test
  void testBuscarCategoriaPorId_Exitoso() throws Exception {
    when(service.buscarCategoria(1L)).thenReturn(categoriaTest);

    mockMvc.perform(get("/api/categorias/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.titulo").value("Categoria Test"));
  }

  @Test
  void testBuscarCategoriaPorId_Inexistente() throws Exception {
    when(service.buscarCategoria(99L)).thenThrow(new RuntimeException("Categoria no encontrada en la base de datos"));

    mockMvc.perform(get("/api/categorias/99"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$").value("Categoria no encontrada en la base de datos"));
  }
}
