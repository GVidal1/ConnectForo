package com.microservicio.foros.microservicio_foros.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

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

import com.microservicio.foros.microservicio_foros.Model.Foros;
import com.microservicio.foros.microservicio_foros.Repository.ForosRepository;
import com.microservicio.foros.microservicio_foros.Services.ForosService;
import com.microservicio.foros.microservicio_foros.client.CategoriaClient;
import com.microservicio.foros.microservicio_foros.client.UsuarioClient;

@ExtendWith(MockitoExtension.class)
public class ForosServiceTest {
  @Mock
  private ForosRepository repository;

  @Mock
  private CategoriaClient categoriaClient;

  @Mock
  private UsuarioClient usuarioClient;

  @InjectMocks
  private ForosService service;

  private Foros foroTest;
  private List<Foros> foroListTest;
  private Map<String, Object> categoriaResponse;
  private Map<String, Object> usuarioResponse;

  @BeforeEach
  void setUp() {
    foroTest = new Foros(1L, 1L, 1L, "Foro Test", "Testing de el servicio foros", LocalDateTime.now());
    foroListTest = Arrays.asList(foroTest);
    
    categoriaResponse = new HashMap<>();
    categoriaResponse.put("id", 1L);
    categoriaResponse.put("titulo", "Categoria Test");
    
    usuarioResponse = new HashMap<>();
    usuarioResponse.put("id", 1L);
    usuarioResponse.put("nombre", "Usuario Test");
  }
  @Test

  void testActualizarForoPorIdYNuevaInfo() {
    Long idExistente = 1L;
    Foros nuevaInfo = new Foros(idExistente, idExistente, idExistente, "Distitnto Titulo", "Testing", LocalDateTime.now());

    when(repository.findById(idExistente)).thenReturn(Optional.of(foroTest));
    when(categoriaClient.obtenerCategoriaPorId(idExistente)).thenReturn(categoriaResponse);
    when(usuarioClient.obtenerUsuarioPorId(idExistente)).thenReturn(usuarioResponse);
    when(repository.save(foroTest)).thenReturn(nuevaInfo);

    Foros result = service.actualizarForo(idExistente, nuevaInfo);

    assertThat(result).isEqualTo(nuevaInfo);
  }

  @Test
  void testGuardarForo_Exitoso() {
    when(categoriaClient.obtenerCategoriaPorId(1L)).thenReturn(categoriaResponse);
    when(usuarioClient.obtenerUsuarioPorId(1L)).thenReturn(usuarioResponse);
    when(repository.save(foroTest)).thenReturn(foroTest);

    Foros result = service.guardarForo(foroTest);

    assertThat(result).isEqualTo(foroTest);
  }


  @Test
  void testListarForos_Existentes() {
    when(repository.findAll()).thenReturn(foroListTest);

    List<Foros> result = service.listarForos();

    assertThat(result).isEqualTo(foroListTest);
  }

  @Test
  void testBuscarForoPorId_Existente() {
    when(repository.findById(1L)).thenReturn(Optional.of(foroTest));

    Foros result = service.buscarForos(1L);

    assertThat(result).isEqualTo(foroTest);
  }

  @Test
  void testBorrarForoPorId_Existente() {
    when(repository.findById(1L)).thenReturn(Optional.of(foroTest));
    doNothing().when(repository).deleteById(1L);

    String result = service.borrarForo(1L);


    assertThat(result).isEqualTo("Foro Eliminado correctamente");
  }

  @Test
  void testBucarForoPorIdUsuario_Existente() {
    when(repository.encontrarForosPorIdUsuario(1L)).thenReturn(foroListTest);
    when(usuarioClient.obtenerUsuarioPorId(1L )).thenReturn(usuarioResponse);

    List<Foros> result = service.buscarForosPorUsuario(1L);

    assertThat(result).isEqualTo(foroListTest);
  }

  @Test
  void testBuscarForosPorPalabraEnTitulo() {
    when(repository.encontrarForosPorPalabraEnTitulo("Test")).thenReturn(foroListTest);

    List<Foros> result = service.buscarForosPorPalabraEnTitulo("Test");

    assertThat(result).isEqualTo(foroListTest);
  }

  @Test
  void testBuscarForosPorPalabraEnContenido_Exitoso() {
    when(repository.encontrarForosPorPalabraEnContenido("Testing")).thenReturn(foroListTest);

    List<Foros> result = service.buscarForosPorPalabraEnContenido("Testing");

    assertThat(result).isEqualTo(foroListTest);
  }

  @Test
  void testBuscarForosPorRangoFechas_Exitoso() {
    LocalDateTime fechaInicio = LocalDateTime.now().minusDays(1);
    LocalDateTime fechaFin = LocalDateTime.now().plusDays(1);
    when(repository.encontrarForosPorRangoFechas(fechaInicio, fechaFin)).thenReturn(foroListTest);

    List<Foros> result = service.buscarForosPorRangoFechas(fechaInicio, fechaFin);

    assertThat(result).isEqualTo(foroListTest);
  }

  @Test
  void testBuscarForosPorCategoriaYUsuario_Exitoso() {
    when(categoriaClient.obtenerCategoriaPorId(1L)).thenReturn(categoriaResponse);
    when(usuarioClient.obtenerUsuarioPorId(1L)).thenReturn(usuarioResponse);
    when(repository.encontrarForosPorCategoriaYUsuario(1L, 1L)).thenReturn(foroListTest);

    List<Foros> result = service.buscarForosPorCategoriaYUsuario(1L, 1L);

    assertThat(result).isEqualTo(foroListTest);
  }

  @Test
  void testBuscarForosPorLongitudTitulo_Exitoso() {
    when(repository.encontrarForosPorLongitudTitulo(5)).thenReturn(foroListTest);

    List<Foros> result = service.buscarForosPorLongitudTitulo(5);

    assertThat(result).isEqualTo(foroListTest);
  }

  @Test
  void testBuscarForosCreadosDespuesDe_Exitoso() {
    LocalDateTime fecha = LocalDateTime.now().minusDays(1);
    when(repository.encontrarForosCreadosDespuesDe(fecha)).thenReturn(foroListTest);

    List<Foros> result = service.buscarForosCreadosDespuesDe(fecha);

    assertThat(result).isEqualTo(foroListTest);
  }

  @Test
  void testBuscarForosCreadosAntesDe_Exitoso() {
    LocalDateTime fecha = LocalDateTime.now().plusDays(1);
    when(repository.encontrarForosCreadosAntesDe(fecha)).thenReturn(foroListTest);

    List<Foros> result = service.buscarForosCreadosAntesDe(fecha);

    assertThat(result).isEqualTo(foroListTest);
  }
}
