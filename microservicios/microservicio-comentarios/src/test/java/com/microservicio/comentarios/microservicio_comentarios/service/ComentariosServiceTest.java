package com.microservicio.comentarios.microservicio_comentarios.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.microservicio.comentarios.microservicio_comentarios.clients.PostClient;
import com.microservicio.comentarios.microservicio_comentarios.clients.UsuarioClient;
import com.microservicio.comentarios.microservicio_comentarios.model.Comentarios;
import com.microservicio.comentarios.microservicio_comentarios.repository.ComentariosRepository;
import com.microservicio.comentarios.microservicio_comentarios.services.ComentariosService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ComentariosServiceTest {

  @Mock
  private ComentariosRepository repository;

  @Mock
  private PostClient postClient;

  @Mock
  private UsuarioClient usuarioClient;

  @InjectMocks
  private ComentariosService service;

  private Comentarios comentarioTest;
  private List<Comentarios> comentariosListTest;
  private Map<String, Object> postResponse;
  private Map<String, Object> usuarioResponse;

  @BeforeEach
  void setUp() {
    comentarioTest = new Comentarios(1L, "Test", 1L, 1L, LocalDateTime.now());
    comentariosListTest = Arrays.asList(comentarioTest);
    postResponse = new HashMap<>();
    postResponse.put("id", 1L);
    postResponse.put("titulo", "responsePost");
    usuarioResponse = new HashMap<>();
    usuarioResponse.put("id", 1L);
    usuarioResponse.put("nombreUsuario", "usuarioTest");
  }

  @Test
  void testListarComentarios() {
    when(repository.findAll()).thenReturn(comentariosListTest);

    List<Comentarios> resultado = service.listarComentarios();

    assertThat(resultado).isEqualTo(comentariosListTest);
    verify(repository).findAll();
  }

  @Test
  void testBuscarComentarioPorId() {
    when(repository.findById(1L)).thenReturn(Optional.of(comentarioTest));

    Comentarios resultado = service.buscarComentarioPorId(1L);

    assertThat(resultado).isEqualTo(comentarioTest);
    verify(repository).findById(1L);
  }

  @Test
  void testBorrarComentarioPorId() {
    when(repository.findById(1L)).thenReturn(Optional.of(comentarioTest));

    String resultado = service.borrarComentarioPorId(1L);

    assertThat(resultado).isEqualTo("Comentario Eliminado con existo");
    verify(repository).deleteById(1L);
  }

  @Test
  void testGuardarComentario() {
    when(postClient.obtenerPostPorId(1L)).thenReturn(postResponse);
    when(usuarioClient.obtenerUsuarioPorId(1L)).thenReturn(usuarioResponse);
    when(repository.save(any(Comentarios.class))).thenReturn(comentarioTest);

    Comentarios resultado = service.guardarComentario(comentarioTest);

    assertThat(resultado).isEqualTo(comentarioTest);
    verify(repository).save(comentarioTest);
  }

  @Test
  void testActualizarComentario() {
    Comentarios comentarioActualizado = new Comentarios(null, "Contenido Actualizado", null, null, null);

    when(repository.findById(1L)).thenReturn(Optional.of(comentarioTest));
    when(repository.save(any(Comentarios.class))).thenReturn(comentarioTest);

    Comentarios resultado = service.actualizarComentario(1L, comentarioActualizado);

    assertThat(resultado).isEqualTo(comentarioTest);
    verify(repository).save(comentarioTest);
  }

  @Test
  void testBuscarPorPalabraEnContenido() {
    when(repository.findByContenidoContainingIgnoreCase("test")).thenReturn(comentariosListTest);

    List<Comentarios> resultado = service.buscarPorPalabraEnContenido("test");

    assertThat(resultado).isEqualTo(comentariosListTest);
    verify(repository).findByContenidoContainingIgnoreCase("test");
  }

  @Test
  void testBuscarPorUsuario() {
    when(usuarioClient.obtenerUsuarioPorId(1L)).thenReturn(usuarioResponse);
    when(repository.findByIdUsuario(1L)).thenReturn(comentariosListTest);

    List<Comentarios> resultado = service.buscarPorUsuario(1L);

    assertThat(resultado).isEqualTo(comentariosListTest);
    verify(repository).findByIdUsuario(1L);
  }

  @Test
  void testBuscarPorPost() {
    when(postClient.obtenerPostPorId(1L)).thenReturn(postResponse);
    when(repository.findByIdPost(1L)).thenReturn(comentariosListTest);

    List<Comentarios> resultado = service.buscarPorPost(1L);

    assertThat(resultado).isEqualTo(comentariosListTest);
    verify(repository).findByIdPost(1L);
  }

  @Test
  void testBuscarPorRangoFechas() {
    LocalDateTime fechaInicio = LocalDateTime.now().minusDays(7);
    LocalDateTime fechaFin = LocalDateTime.now();

    when(repository.findByFechaCreacionBetween(fechaInicio, fechaFin)).thenReturn(comentariosListTest);

    List<Comentarios> resultado = service.buscarPorRangoFechas(fechaInicio, fechaFin);

    assertThat(resultado).isEqualTo(comentariosListTest);
    verify(repository).findByFechaCreacionBetween(fechaInicio, fechaFin);
  }

  @Test
  void testBuscarCreadosDespuesDe() {
    LocalDateTime fecha = LocalDateTime.now().minusDays(7);

    when(repository.findByFechaCreacionAfter(fecha)).thenReturn(comentariosListTest);

    List<Comentarios> resultado = service.buscarCreadosDespuesDe(fecha);

    assertThat(resultado).isEqualTo(comentariosListTest);
    verify(repository).findByFechaCreacionAfter(fecha);
  }

  @Test
  void testBuscarCreadosAntesDe() {
    LocalDateTime fecha = LocalDateTime.now().plusDays(7);

    when(repository.findByFechaCreacionBefore(fecha)).thenReturn(comentariosListTest);

    List<Comentarios> resultado = service.buscarCreadosAntesDe(fecha);

    assertThat(resultado).isEqualTo(comentariosListTest);
    verify(repository).findByFechaCreacionBefore(fecha);
  }

  @Test
  void testBuscarPorLongitudContenido() {
    when(repository.findByLongitudContenidoMayorA(5)).thenReturn(comentariosListTest);

    List<Comentarios> resultado = service.buscarPorLongitudContenido(5);

    assertThat(resultado).isEqualTo(comentariosListTest);
    verify(repository).findByLongitudContenidoMayorA(5);
  }

  @Test
  void testBuscarPorUsuarioYPost() {
    when(usuarioClient.obtenerUsuarioPorId(1L)).thenReturn(usuarioResponse);
    when(postClient.obtenerPostPorId(1L)).thenReturn(postResponse);
    when(repository.findByIdUsuarioAndIdPost(1L, 1L)).thenReturn(comentariosListTest);

    List<Comentarios> resultado = service.buscarPorUsuarioYPost(1L, 1L);

    assertThat(resultado).isEqualTo(comentariosListTest);
    verify(repository).findByIdUsuarioAndIdPost(1L, 1L);
  }

  @Test
  void testContarComentariosPorPost() {
    when(postClient.obtenerPostPorId(1L)).thenReturn(postResponse);
    when(repository.countByIdPost(1L)).thenReturn(5L);

    Long resultado = service.contarComentariosPorPost(1L);

    assertThat(resultado).isEqualTo(5L);
    verify(repository).countByIdPost(1L);
  }

  @Test
  void testContarComentariosPorUsuario() {
    when(usuarioClient.obtenerUsuarioPorId(1L)).thenReturn(usuarioResponse);
    when(repository.countByIdUsuario(1L)).thenReturn(3L);

    Long resultado = service.contarComentariosPorUsuario(1L);

    assertThat(resultado).isEqualTo(3L);
    verify(repository).countByIdUsuario(1L);
  }
}
