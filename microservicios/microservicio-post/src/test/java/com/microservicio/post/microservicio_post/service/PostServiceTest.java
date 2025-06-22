package com.microservicio.post.microservicio_post.service;

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

import com.microservicio.post.microservicio_post.client.ForoClient;
import com.microservicio.post.microservicio_post.client.UsuarioClient;
import com.microservicio.post.microservicio_post.model.Post;
import com.microservicio.post.microservicio_post.repository.PostRepository;
import com.microservicio.post.microservicio_post.services.PostService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

  @Mock
  private PostRepository repository;

  @Mock
  private ForoClient foroClient;

  @Mock
  private UsuarioClient usuarioClient;

  @InjectMocks
  private PostService service;

  private Post postTest;
  private List<Post> postListTest;
  private Map<String, Object> foroResponse;
  private Map<String, Object> usuarioResponse;

  @BeforeEach
  void setUp() {
    postTest = new Post(1L, "Test Post", "Contenido de prueba", 1L, 1L, LocalDateTime.now());
    postListTest = Arrays.asList(postTest);

    foroResponse = new HashMap<>();
    foroResponse.put("id", 1L);
    foroResponse.put("nombre", "Foro Test");

    usuarioResponse = new HashMap<>();
    usuarioResponse.put("id", 1L);
    usuarioResponse.put("nombre", "Usuario Test");
  }

  @Test
  void testListarPublicaciones() {
    when(repository.findAll()).thenReturn(postListTest);

    List<Post> resultado = service.listarPublicaciones();

    assertThat(resultado).isEqualTo(postListTest);
    verify(repository).findAll();
  }

  @Test
  void testBuscarPublicacionPorId() {
    when(repository.findById(1L)).thenReturn(Optional.of(postTest));

    Post resultado = service.buscarPublicacionPorId(1L);

    assertThat(resultado).isEqualTo(postTest);
    verify(repository).findById(1L);
  }

  @Test
  void testBorrarPublicacionPorId() {
    when(repository.findById(1L)).thenReturn(Optional.of(postTest));

    String resultado = service.borrarPubliacionPorId(1L);

    assertThat(resultado).isEqualTo("Publicacion Eliminada");
    verify(repository).deleteById(1L);
  }

  @Test
  void testGuardarPublicacion() {
    when(foroClient.obtenerForoPorId(1L)).thenReturn(foroResponse);
    when(usuarioClient.obtenerUsuarioPorId(1L)).thenReturn(usuarioResponse);
    when(repository.save(any(Post.class))).thenReturn(postTest);

    Post resultado = service.guardarPublicacion(postTest);

    assertThat(resultado).isEqualTo(postTest);
    verify(repository).save(postTest);
  }

  @Test
  void testActualizarPost() {
    Post postActualizado = new Post(null, "Título Actualizado", "Contenido Actualizado", null, null, null);

    when(repository.findById(1L)).thenReturn(Optional.of(postTest));
    when(repository.save(any(Post.class))).thenReturn(postTest);
    
    Post resultado = service.actualizarPost(1L, postActualizado);

    assertThat(resultado).isEqualTo(postTest);
    verify(repository).save(postTest);
  }

  @Test
  void testBuscarPorPalabraEnTitulo() {
    when(repository.findByTituloContainingIgnoreCase("test")).thenReturn(postListTest);

    List<Post> resultado = service.buscarPorPalabraEnTitulo("test");

    assertThat(resultado).isEqualTo(postListTest);
    verify(repository).findByTituloContainingIgnoreCase("test");
  }

  @Test
  void testBuscarPorPalabraEnContenido() {
    when(repository.findByContenidoContainingIgnoreCase("prueba")).thenReturn(postListTest);

    List<Post> resultado = service.buscarPorPalabraEnContenido("prueba");

    assertThat(resultado).isEqualTo(postListTest);
    verify(repository).findByContenidoContainingIgnoreCase("prueba");
  }

  @Test
  void testBuscarPorUsuario() {
    when(usuarioClient.obtenerUsuarioPorId(1L)).thenReturn(usuarioResponse);
    when(repository.findByIdUsuario(1L)).thenReturn(postListTest);

    List<Post> resultado = service.buscarPorUsuario(1L);

    assertThat(resultado).isEqualTo(postListTest);
    verify(repository).findByIdUsuario(1L);
  }

  @Test
  void testBuscarPorForo() {
    when(foroClient.obtenerForoPorId(1L)).thenReturn(foroResponse);
    when(repository.findByIdForo(1L)).thenReturn(postListTest);

    List<Post> resultado = service.buscarPorForo(1L);

    assertThat(resultado).isEqualTo(postListTest);
    verify(repository).findByIdForo(1L);
  }

  @Test
  void testBuscarPorRangoFechas() {
    LocalDateTime fechaInicio = LocalDateTime.now().minusDays(7);
    LocalDateTime fechaFin = LocalDateTime.now();

    when(repository.findByFechaCreacionBetween(fechaInicio, fechaFin)).thenReturn(postListTest);

    List<Post> resultado = service.buscarPorRangoFechas(fechaInicio, fechaFin);

    assertThat(resultado).isEqualTo(postListTest);
    verify(repository).findByFechaCreacionBetween(fechaInicio, fechaFin);
  }

  @Test
  void testBuscarCreadosDespuesDe() {
    LocalDateTime fecha = LocalDateTime.now().minusDays(7);

    when(repository.findByFechaCreacionAfter(fecha)).thenReturn(postListTest);

    List<Post> resultado = service.buscarCreadosDespuesDe(fecha);

    assertThat(resultado).isEqualTo(postListTest);
    verify(repository).findByFechaCreacionAfter(fecha);
  }

  @Test
  void testBuscarCreadosAntesDe() {
    LocalDateTime fecha = LocalDateTime.now().plusDays(7);

    when(repository.findByFechaCreacionBefore(fecha)).thenReturn(postListTest);

    List<Post> resultado = service.buscarCreadosAntesDe(fecha);

    assertThat(resultado).isEqualTo(postListTest);
    verify(repository).findByFechaCreacionBefore(fecha);
  }

  @Test
  void testBuscarPorLongitudTitulo() {
    when(repository.findByLongitudTituloMayorA(5)).thenReturn(postListTest);

    List<Post> resultado = service.buscarPorLongitudTitulo(5);

    assertThat(resultado).isEqualTo(postListTest);
    verify(repository).findByLongitudTituloMayorA(5);
  }

  @Test
  void testBuscarPorUsuarioYForo() {
    when(usuarioClient.obtenerUsuarioPorId(1L)).thenReturn(usuarioResponse);
    when(foroClient.obtenerForoPorId(1L)).thenReturn(foroResponse);
    when(repository.findByIdUsuarioAndIdForo(1L, 1L)).thenReturn(postListTest);

    List<Post> resultado = service.buscarPorUsuarioYForo(1L, 1L);

    assertThat(resultado).isEqualTo(postListTest);
    verify(repository).findByIdUsuarioAndIdForo(1L, 1L);
  }
}
