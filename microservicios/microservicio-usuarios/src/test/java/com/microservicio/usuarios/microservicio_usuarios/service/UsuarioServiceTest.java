package com.microservicio.usuarios.microservicio_usuarios.service;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import org.springframework.security.crypto.password.PasswordEncoder;

import com.microservicio.usuarios.microservicio_usuarios.client.RolClient;
import com.microservicio.usuarios.microservicio_usuarios.model.Usuarios;
import com.microservicio.usuarios.microservicio_usuarios.repository.UsuarioRepository;
import com.microservicio.usuarios.microservicio_usuarios.dto.CambiarPasswordDTO;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

  @Mock
  private UsuarioRepository repository;

  @Mock
  private RolClient rolClient;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserService service;

  private Usuarios usuarioTest;
  private List<Usuarios> usuarioTestList;
  private Map<String, Object> rolResponse;

  @BeforeEach
  void setUp() {
    usuarioTest = new Usuarios(1L , 1L , "Test usuario", "test@email.com", "passwordTest", LocalDateTime.now(), "gabriel", "vidal");
    usuarioTestList = Arrays.asList(usuarioTest);

    rolResponse = new HashMap<>();
    rolResponse.put("id", 1L );
    rolResponse.put("nombreRol", "ADMIN");
  }


  @Test
  void testListarUsuarios_Existentes() {
    when(repository.findAll()).thenReturn(usuarioTestList);

    List<Usuarios> result = service.listarUsuarios();

    assertThat(result).isEqualTo(usuarioTestList);
  }

  @Test
  void testObtenerUsuarioPorId_Existente() {
    when(repository.findById(1L)).thenReturn(Optional.of(usuarioTest));

    Usuarios result = service.obtenerUsuarioPorId(1L);

    assertThat(result).isEqualTo(usuarioTest);
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(usuarioTest.getId());
  }

  @Test
  void testobtenerUsuarioPorCorreo_Existente() {
    when(repository.findByCorreo("test@email.com")).thenReturn(Optional.of(usuarioTest));

    Usuarios result = service.obtenerUsuarioPorCorreo("test@email.com");

    assertThat(result).isEqualTo(usuarioTest);
    assertThat(result.getId()).isEqualTo(usuarioTest.getId());
  }

  @Test
  void testObtenrUSuarioPorIdRol_Existente() {
    when(rolClient.obtenerRolPorId(1L)).thenReturn(rolResponse);
    when(repository.findByIdRol(1L)).thenReturn(usuarioTestList);

    List<Usuarios> result = service.obtenerUsuariosPorRol(1L);

    assertThat(result).isEqualTo(usuarioTestList);
    assertThat(result.get(0).getId()).isEqualTo(usuarioTestList.get(0).getId());
  }

  @Test
  void testBorrarUsuarioPorId_Existente() {
    when(repository.findById(1L)).thenReturn(Optional.of(usuarioTest));
    doNothing().when(repository).deleteById(1L);

    String result = service.borrarUsuario(1L);

    assertThat(result).isEqualTo("Se ha eliminado el usuario correctamente.");
  }

  @Test
  void testGuardarUsuario_Correcto() {
    when(rolClient.obtenerRolPorId(1L)).thenReturn(rolResponse);
    when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
    Usuarios usuarioConPasswordCodificado = new Usuarios(1L , 1L , "Test usuario", "test@email.com", "encodedPassword", usuarioTest.getFechaCreacion(), "gabriel", "vidal");
    when(repository.save(usuarioConPasswordCodificado)).thenReturn(usuarioConPasswordCodificado);
    
    Usuarios result = service.guardarUsuario(usuarioTest);

    assertThat(result.getPassword()).isEqualTo("encodedPassword");
    assertThat(result.getId()).isEqualTo(usuarioTest.getId());
  }


  @Test
  void testActualizarInfoUsuarioPorId_Existente() {
    when(rolClient.obtenerRolPorId(1L)).thenReturn(rolResponse);
    when(repository.findById(1L)).thenReturn(Optional.of(usuarioTest));
    when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
    Usuarios usuarioActualizado = new Usuarios(1L , 1L , "Test usuario", "test@email.com", "encodedPassword", usuarioTest.getFechaCreacion(), "gabriel", "vidal");

    when(repository.save(usuarioActualizado)).thenReturn(usuarioActualizado);

    Usuarios result = service.actualizarUsuario(1L, usuarioActualizado);

    assertThat(result).isEqualTo(usuarioActualizado);
    assertThat(result.getId()).isEqualTo(usuarioActualizado.getId());
  }

  @Test
  void testBuscarUsuariosPorNombreOCorreo_Existentes() {
    when(repository.findByNombreUsuarioContainingOrCorreoContaining("gabriel", "")).thenReturn(usuarioTestList);

    List<Usuarios> result = service.buscarUsuarios("gabriel", "");

    assertThat(result).isEqualTo(usuarioTestList);
  }

  @Test
  void testCambiarPasswordPorId_Existente() {
    CambiarPasswordDTO dto = new CambiarPasswordDTO("passwordTest", "nuevaPassword", "nuevaPassword");
    when(repository.findById(1L)).thenReturn(Optional.of(usuarioTest));
    when(passwordEncoder.matches(dto.getPasswordActual(), usuarioTest.getPassword())).thenReturn(true);
    when(passwordEncoder.encode("nuevaPassword")).thenReturn("encodedNuevaPassword");
    Usuarios usuarioConPasswordNuevo = new Usuarios(1L, 1L, "Test usuario", "test@email.com", "encodedNuevaPassword", usuarioTest.getFechaCreacion(), "gabriel", "vidal");
    when(repository.save(usuarioTest)).thenReturn(usuarioConPasswordNuevo);

    Usuarios result = service.cambiarPassword(1L, dto);

    assertThat(result.getPassword()).isEqualTo("encodedNuevaPassword");
    assertThat(result.getId()).isEqualTo(usuarioTest.getId());
  }

  @Test
  void testSolicitarRecuperacionPassword_PorCorreoExistente() {
    when(repository.findByCorreo("test@email.com")).thenReturn(Optional.of(usuarioTest));

    service.solicitarRecuperacionPassword("test@email.com");
  }

  @Test
  void testSolicitarRecuperacionPassword_PorCorreoInexistente() {
    when(repository.findByCorreo("noexiste@email.com")).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        service.solicitarRecuperacionPassword("noexiste@email.com");
    });


    assertThat(exception.getMessage()).isEqualTo("No se ha encontrado un usuario con ese correo.");
  }
}
