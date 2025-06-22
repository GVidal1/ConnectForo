package com.microservicio.post.microservicio_post.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicio.post.microservicio_post.model.Post;
import com.microservicio.post.microservicio_post.services.PostService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
public class PostControllerTest {

  @MockBean
  private PostService postService;

  @InjectMocks
  private PostController controller;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private Post postTest;
  private List<Post> postListTest;

  @BeforeEach
  void setUp() {
    postTest = new Post(1L, "Test Post", "Contenido de prueba", 1L, 1L, LocalDateTime.now());
    postListTest = Arrays.asList(postTest);
  }

  @Test
  void testListarPublicaciones_JsonAndOK() throws Exception {
    when(postService.listarPublicaciones()).thenReturn(postListTest);

    mockMvc.perform(get("/api/publicaciones"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].titulo").value("Test Post"))
        .andExpect(jsonPath("$[0].contenido").value("Contenido de prueba"))
        .andExpect(jsonPath("$[0].idForo").value(1))
        .andExpect(jsonPath("$[0].idUsuario").value(1));
  }

  @Test
  void testObtenerPublicacion_JsonAndOK() throws Exception {
    when(postService.buscarPublicacionPorId(1L)).thenReturn(postTest);

    mockMvc.perform(get("/api/publicaciones/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.titulo").value("Test Post"))
        .andExpect(jsonPath("$.contenido").value("Contenido de prueba"))
        .andExpect(jsonPath("$.idForo").value(1))
        .andExpect(jsonPath("$.idUsuario").value(1));
  }

  @Test
  void testCrearPublicacion_JsonAndCreated() throws Exception {
    when(postService.guardarPublicacion(any(Post.class))).thenReturn(postTest);

    mockMvc.perform(post("/api/publicaciones")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(postTest)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.titulo").value("Test Post"))
        .andExpect(jsonPath("$.contenido").value("Contenido de prueba"));
  }

  @Test
  void testActualizarPublicacion_JsonAndOK() throws Exception {
    when(postService.actualizarPost(anyLong(), any(Post.class))).thenReturn(postTest);

    mockMvc.perform(put("/api/publicaciones/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(postTest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.titulo").value("Test Post"))
        .andExpect(jsonPath("$.contenido").value("Contenido de prueba"));
  }

  @Test
  void testBorrarPublicacion_StringAndNoContent() throws Exception {
    when(postService.borrarPubliacionPorId(1L)).thenReturn("Publicacion Eliminada");

    mockMvc.perform(delete("/api/publicaciones/1"))
        .andExpect(status().isOk())
        .andExpect(content().string("Publicacion Eliminada"));
  }

  @Test
  void testBuscarPorPalabraEnTitulo_JsonAndOK() throws Exception {
    when(postService.buscarPorPalabraEnTitulo(anyString())).thenReturn(postListTest);

    mockMvc.perform(get("/api/publicaciones/buscar/titulo")
        .param("palabra", "React"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].titulo").value("Test Post"));
  }

  @Test
  void testBuscarPorPalabraEnContenido_JsonAndOK() throws Exception {
    when(postService.buscarPorPalabraEnContenido(anyString())).thenReturn(postListTest);

    mockMvc.perform(get("/api/publicaciones/buscar/contenido")
        .param("palabra", "Desarrollo"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].contenido").value("Contenido de prueba"));
  }

  @Test
  void testBuscarPorUsuario_JsonAndOK() throws Exception {
    when(postService.buscarPorUsuario(anyLong())).thenReturn(postListTest);

    mockMvc.perform(get("/api/publicaciones/usuario/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].idUsuario").value(1));
  }

  @Test
  void testBuscarPorForo_JsonAndOK() throws Exception {
    when(postService.buscarPorForo(anyLong())).thenReturn(postListTest);

    mockMvc.perform(get("/api/publicaciones/foro/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].idForo").value(1));
  }

  @Test
  void testBuscarPorRangoFechas_JsonAndOK() throws Exception {
    LocalDateTime fechaInicio = LocalDateTime.now().minusDays(7);
    LocalDateTime fechaFin = LocalDateTime.now();
    when(postService.buscarPorRangoFechas(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(postListTest);

    mockMvc.perform(get("/api/publicaciones/buscar/fechas")
        .param("fechaInicio", fechaInicio.toString())
        .param("fechaFin", fechaFin.toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1));
  }

  @Test
  void testBuscarCreadosDespuesDe_JsonAndOK() throws Exception {
    LocalDateTime fecha = LocalDateTime.now().minusDays(7);
    when(postService.buscarCreadosDespuesDe(any(LocalDateTime.class))).thenReturn(postListTest);

    mockMvc.perform(get("/api/publicaciones/buscar/despues-de")
        .param("fecha", fecha.toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1));
  }

  @Test
  void testBuscarCreadosAntesDe_JsonAndOK() throws Exception {
    LocalDateTime fecha = LocalDateTime.now().plusDays(7);
    when(postService.buscarCreadosAntesDe(any(LocalDateTime.class))).thenReturn(postListTest);

    mockMvc.perform(get("/api/publicaciones/buscar/antes-de")
        .param("fecha", fecha.toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1));
  }

  @Test
  void testBuscarPorLongitudTitulo_JsonAndOK() throws Exception {
    when(postService.buscarPorLongitudTitulo(anyInt())).thenReturn(postListTest);

    mockMvc.perform(get("/api/publicaciones/buscar/longitud-titulo")
        .param("longitud", "5"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1));
  }

  @Test
  void testBuscarPorUsuarioYForo_JsonAndOK() throws Exception {
    when(postService.buscarPorUsuarioYForo(anyLong(), anyLong())).thenReturn(postListTest);

    mockMvc.perform(get("/api/publicaciones/buscar/usuario-foro")
        .param("idUsuario", "1")
        .param("idForo", "1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].idUsuario").value(1))
        .andExpect(jsonPath("$[0].idForo").value(1));
  }
} 