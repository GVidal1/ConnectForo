package com.microservicio.foros.microservicio_foros.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.print.attribute.standard.Media;

import java.util.Arrays;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicio.foros.microservicio_foros.Model.Foros;
import com.microservicio.foros.microservicio_foros.Services.ForosService;
// import com.microservicio.foros.microservicio_foros.client.CategoriaClient;
// import com.microservicio.foros.microservicio_foros.client.UsuarioClient;

@WebMvcTest(ForosController.class)
public class ForosControllerTest {

  @MockBean
  private ForosService service;

  // @MockBean
  // private CategoriaClient categoriaClient;

  // @MockBean
  // private UsuarioClient usuarioClient;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private Foros foroTest;
  private List<Foros> foroListTest;
  // private Map<String, Object> categoriaResponse;
  // private Map<String, Object> usuarioResponse;

  @BeforeEach
  void setUp() {
    foroTest = new Foros(1L, 1L, 1L, "Foro Test", "Controller testing", LocalDateTime.now());
    foroListTest = Arrays.asList(foroTest);
    // categoriaResponse = new HashMap<>();
    // categoriaResponse.put( "id", 1L);
    // usuarioResponse = new HashMap<>();
    // usuarioResponse.put("id", 1L);
  }
  // GET - /api/foros
  @Test
  void testObtenerListaForos_JsonAndOK() throws Exception {
    when(service.listarForos()).thenReturn(foroListTest);

    mockMvc.perform(get("/api/foros"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1L));
  }

  // GET - /api/foros/{id}
  @Test
  void testObtenerForoPorId_JsonAndOK() throws Exception {
    
    when(service.buscarForos(1L)).thenReturn(foroTest);

    mockMvc.perform(get("/api/foros/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L));
  }

  // POST - /api/foros
  @Test
  void testCrearForoNuevo_JsonAndCreated() throws Exception {
    // when(categoriaClient.obtenerCategoriaPorId(1L)).thenReturn(categoriaResponse);
    // when(usuarioClient.obtenerUsuarioPorId(1L)).thenReturn(usuarioResponse);
    when(service.guardarForo(foroTest)).thenReturn(foroTest);

    mockMvc.perform(post("/api/foros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(foroTest)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.titulo").value("Foro Test"));
  }

  // PUT - /api/foros/{id}
  @Test
  void testActualizarForoPorId_JsonAndOK() throws Exception{
    Foros nuevaInfo = new Foros(1L, 1L, 1L, "Nuevo Titulo", "testing", LocalDateTime.now());

    when(service.actualizarForo(1L, nuevaInfo)).thenReturn(nuevaInfo);

    mockMvc.perform(put("/api/foros/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(nuevaInfo)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.titulo").value("Nuevo Titulo"));
  }

  // DELETE - /api/foros/{id}
  @Test 
  void testEliminarForoPorId_NoContent() throws Exception {
    when(service.borrarForo(1L)).thenReturn("Foro Eliminado correctamente");

    mockMvc.perform(delete("/api/foros/1"))
            .andExpect(status().isNoContent());
  }

  // GET - /api/foros/usuario/{idUsuario}
  @Test
  void testBuscarForosPorIdUsuario_ListJsonAndOK() throws Exception {
    when(service.buscarForosPorUsuario(1L)).thenReturn(foroListTest);

    mockMvc.perform(get("/api/foros/usuario/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].titulo").value("Foro Test"));
  }

  // GET - /api/foros/buscar/titulo?palabra=
  @Test
  void testBuscarForosPorPalabraEnTitulo_JsonAndOK() throws Exception{
    when(service.buscarForosPorPalabraEnTitulo("Test")).thenReturn(foroListTest);

    mockMvc.perform(get("/api/foros/buscar/titulo?palabra=Test"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1L));
  }

  // GET - /api/foros/buscar/contenido?palabra=Desarrollo
  @Test
  void testBuscarForoPorPalabraEnContenido_JsonAndOK() throws Exception {
    when(service.buscarForosPorPalabraEnContenido("test")).thenReturn(foroListTest);

    mockMvc.perform(get("/api/foros/buscar/contenido?palabra=test"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].contenido").value("Controller testing"));
  }

  // GET - /api/foros/buscar/fechas?fechaInicio=2024-01-01T00:00:00&fechaFin=2024-12-31T23:59:59
  @Test
  void testBuscarForosPorRangoFechas_JsonAndOK() throws Exception {
    // LocalDateTime fechaInicio = LocalDateTime.of(2024, 1, 1, 0, 0);
    LocalDateTime fechaInicio = LocalDateTime.now().plusDays(10);
    LocalDateTime fechaFin = LocalDateTime.now().minusDays(10);
    when(service.buscarForosPorRangoFechas(fechaInicio, fechaFin)).thenReturn(foroListTest);

    mockMvc.perform(get("/api/foros/buscar/fechas")
            .param("fechaInicio", fechaInicio.toString())
            .param("fechaFin", fechaFin.toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1L));
  }

  // GET - /api/foros/buscar/categoria-usuario?idCategoria=1&idUsuario=1
  @Test
  void testBuscarForosPorCategoriaYUsuario_JsonAndOK() throws Exception {
    when(service.buscarForosPorCategoriaYUsuario(1L, 1L)).thenReturn(foroListTest);

    mockMvc.perform(get("/api/foros/buscar/categoria-usuario")
            .param("idCategoria", "1")
            .param("idUsuario", "1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1L));
  }

  // GET - /api/foros/buscar/longitud-titulo?longitud=5
  @Test
  void testBuscarForosPorLongitudTitulo_JsonAndOK() throws Exception {
    when(service.buscarForosPorLongitudTitulo(5)).thenReturn(foroListTest);

    mockMvc.perform(get("/api/foros/buscar/longitud-titulo")
            .param("longitud", "5"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1L));
  }

  // GET - /api/foros/buscar/despues-de?fecha=2024-01-01T00:00:00
  @Test
  void testBuscarForosCreadosDespuesDe_JsonAndOK() throws Exception {
    LocalDateTime fecha = LocalDateTime.now().minusDays(1);
    when(service.buscarForosCreadosDespuesDe(fecha)).thenReturn(foroListTest);

    mockMvc.perform(get("/api/foros/buscar/despues-de")
            .param("fecha", fecha.toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1L));
  }

  // GET - /api/foros/buscar/antes-de?fecha=2024-01-01T00:00:00
  @Test
  void testBuscarForosCreadosAntesDe_JsonAndOK() throws Exception {
    LocalDateTime fecha = LocalDateTime.now().plusDays(10);
    when(service.buscarForosCreadosAntesDe(fecha)).thenReturn(foroListTest);

    mockMvc.perform(get("/api/foros/buscar/antes-de")
            .param("fecha", fecha.toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1L));
  }
}
