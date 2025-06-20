package com.microservicio.roles.microservicio_roles.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

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

import com.microservicio.roles.microservicio_roles.clients.UsuarioClient;
import com.microservicio.roles.microservicio_roles.dto.UsuarioDTO;
import com.microservicio.roles.microservicio_roles.model.Rol;
import com.microservicio.roles.microservicio_roles.repository.RolRepository;

@ExtendWith(MockitoExtension.class)
public class RolServiceTest {

  @Mock
  private RolRepository repository;

  @Mock
  private UsuarioClient usuarioClient;

  @InjectMocks
  private RolService service;

  private Rol rolTest;
  private Rol rolTes2;
  private List<Rol> rolListTest;
  private UsuarioDTO usuarioDTO;
  private List<UsuarioDTO> usuariosListTest;

  @BeforeEach
  void setUp() {
    rolTest = new Rol(1L, "ADMIN");
    rolTes2 = new Rol(2L, "USUARIO");

    rolListTest = Arrays.asList(rolTest, rolTes2);

    usuarioDTO = new UsuarioDTO(1L, 1L, "usuarioTest", "correo@test.com");
    usuariosListTest = Arrays.asList(usuarioDTO);

  }

  @Test
  void testListarRoles_Existentes() {
    when(repository.findAll()).thenReturn(rolListTest);

    List<Rol> result = service.listarRoles();

    assertThat(result).isEqualTo(rolListTest);
    assertThat(result.get(0).getIdRol()).isEqualTo(rolListTest.get(0).getIdRol());
  }

  @Test
  void testObtenerRolPorId_Existente(){
    when(repository.findById(1L)).thenReturn(Optional.of(rolTest));

    Rol result = service.obtenerRolPorId(1L);

    assertThat(result).isEqualTo(rolTest);
    assertThat(result.getTipoRol()).isEqualTo(rolTest.getTipoRol());
  }

  @Test
  void testGuardarRolNuevo() {
    when(repository.save(rolTes2)).thenReturn(rolTes2);

    Rol result = service.guardarRol(rolTes2);

    assertThat(result).isEqualTo(rolTes2);
    assertThat(result.getIdRol()).isEqualTo(rolTes2.getIdRol());
  }

  @Test
  void testActualzarRolPorId_Existente() {
    Rol nuevaInfoRol = new Rol(1L, "TEST");
    when(repository.findById(1L)).thenReturn(Optional.of(rolTest));
    when(repository.save(nuevaInfoRol)).thenReturn(nuevaInfoRol);

    Rol result = service.actualizarRol(1L, nuevaInfoRol);

    assertThat(result).isEqualTo(rolTest);
    assertThat(result.getTipoRol()).isEqualTo(nuevaInfoRol.getTipoRol());
  }

  @Test
  void testBorrarRolPorId_Existente() {
    doNothing().when(repository).deleteById(1L);

    String result = service.borrarRol(1L);

    assertThat(result).isEqualTo("Rol eliminado correctamente");
  }

  @Test
  void testObtenerListaUsuariosPorIdRol_Existente() {
    when(repository.findById(1L)).thenReturn(Optional.of(rolTest));
    when(usuarioClient.obtenerUsuariosPorRol(1L)).thenReturn(usuariosListTest);

    List<UsuarioDTO> result = service.obtenerUsuariosPorRol(1L);

    assertThat(result).isEqualTo(usuariosListTest);
    assertThat(result.get(0).getNombreUsuario()).isEqualTo(usuariosListTest.get(0).getNombreUsuario());
  }
}
