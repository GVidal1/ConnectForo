package com.microservicio.comentarios.microservicio_comentarios.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicio.comentarios.microservicio_comentarios.model.Comentarios;
import com.microservicio.comentarios.microservicio_comentarios.services.ComentariosService;

@WebMvcTest(ComentariosController.class)
public class ComentarioControllerTest {

  @MockBean
  private ComentariosService service;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private Comentarios comentarioTest;
  private List<Comentarios> comentariosListTest;

  @BeforeEach
  void setUp() {
    comentarioTest = new Comentarios(1L, "Test", 1L, 1L, LocalDateTime.now());
    comentariosListTest = Arrays.asList(comentarioTest);
  }

  @Test
  void testListaDeComentarios_JsonListAnd200() throws Exception {
    when(service.listarComentarios()).thenReturn(comentariosListTest);

    mockMvc.perform(get("/api/comentarios"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].contenido").value("Test"));
  }

  @Test
  void testBuscarComentarioPorId_JsonAnd200() throws Exception {
    when(service.buscarComentarioPorId(1L)).thenReturn(comentarioTest);

    mockMvc.perform(get("/api/comentarios/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.contenido").value("Test"));
  }

  @Test
  void testEliminarComentarioPorId_NoContent() throws Exception {
    when(service.borrarComentarioPorId(1L)).thenReturn("Comentario Eliminado con existo");

    mockMvc.perform(delete("/api/comentarios/1"))
        .andExpect(status().isNoContent());
  }

  @Test
  void testCrearNuevoComentario_JsonAnd201() throws Exception {
    when(service.guardarComentario(comentarioTest)).thenReturn(comentarioTest);

    mockMvc.perform(post("/api/comentarios")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(comentarioTest)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.contenido").value("Test"));
  }

  @Test
  void testActualizarComentarioPorId_JsonAnd200() throws Exception {
    when(service.actualizarComentario(1L, comentarioTest)).thenReturn(comentarioTest);

    mockMvc.perform(put("/api/comentarios/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(comentarioTest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.contenido").value("Test"));
  }

  @Test
  void testBuscarPorPalabraEnContenido_JsonListAnd200() throws Exception {
    when(service.buscarPorPalabraEnContenido("Desarrollo")).thenReturn(comentariosListTest);

    mockMvc.perform(get("/api/comentarios/buscar/contenido")
        .param("palabra", "Desarrollo"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].contenido").value("Test"));
  }

  @Test
  void testBuscarPorUsuario_JsonListAnd200() throws Exception {
    when(service.buscarPorUsuario(1L)).thenReturn(comentariosListTest);

    mockMvc.perform(get("/api/comentarios/usuario/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].idUsuario").value(1));
  }

  @Test
  void testBuscarPorPost_JsonListAnd200() throws Exception {
    when(service.buscarPorPost(1L)).thenReturn(comentariosListTest);

    mockMvc.perform(get("/api/comentarios/post/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].idPost").value(1));
  }

  @Test
  void testBuscarPorRangoFechas_JsonListAnd200() throws Exception {
    LocalDateTime fechaInicio = LocalDateTime.now().minusDays(7);
    LocalDateTime fechaFin = LocalDateTime.now();
    when(service.buscarPorRangoFechas(fechaInicio, fechaFin)).thenReturn(comentariosListTest);

    mockMvc.perform(get("/api/comentarios/buscar/fechas")
        .param("fechaInicio", fechaInicio.toString())
        .param("fechaFin", fechaFin.toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1));
  }

  @Test
  void testBuscarCreadosDespuesDe_JsonListAnd200() throws Exception {
    LocalDateTime fecha = LocalDateTime.now().minusDays(7);
    when(service.buscarCreadosDespuesDe(fecha)).thenReturn(comentariosListTest);

    mockMvc.perform(get("/api/comentarios/buscar/despues-de")
        .param("fecha", fecha.toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1));
  }

  @Test
  void testBuscarCreadosAntesDe_JsonListAnd200() throws Exception {
    LocalDateTime fecha = LocalDateTime.now().plusDays(7);
    when(service.buscarCreadosAntesDe(fecha)).thenReturn(comentariosListTest);

    mockMvc.perform(get("/api/comentarios/buscar/antes-de")
        .param("fecha", fecha.toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1));
  }

  @Test
  void testBuscarPorLongitudContenido_JsonListAnd200() throws Exception {
    when(service.buscarPorLongitudContenido(5)).thenReturn(comentariosListTest);

    mockMvc.perform(get("/api/comentarios/buscar/longitud-contenido")
        .param("longitud", "5"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1));
  }

  @Test
  void testBuscarPorUsuarioYPost_JsonListAnd200() throws Exception {
    when(service.buscarPorUsuarioYPost(1L, 1L)).thenReturn(comentariosListTest);

    mockMvc.perform(get("/api/comentarios/buscar/usuario-post")
        .param("idUsuario", "1")
        .param("idPost", "1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].idUsuario").value(1))
        .andExpect(jsonPath("$[0].idPost").value(1));
  }

  @Test
  void testContarComentariosPorPost_LongAnd200() throws Exception {
    when(service.contarComentariosPorPost(1L)).thenReturn(5L);

    mockMvc.perform(get("/api/comentarios/contar/post/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").value(5));
  }

  @Test
  void testContarComentariosPorUsuario_LongAnd200() throws Exception {
    when(service.contarComentariosPorUsuario(1L)).thenReturn(3L);

    mockMvc.perform(get("/api/comentarios/contar/usuario/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").value(3));
  }
}
