package com.microservicio.roles.microservicio_roles.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.microservicio.roles.microservicio_roles.dto.UsuarioDTO;
import com.microservicio.roles.microservicio_roles.model.Rol;
import com.microservicio.roles.microservicio_roles.service.RolService;

@WebMvcTest(RolController.class)
public class RolControllerTest {

  @MockBean
  private RolService service;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private Rol rolTest;
  private Rol rolTest2;
  private List<Rol> rolListTest;
  private UsuarioDTO usuarioTest;
  private UsuarioDTO usuarioTest2;
  private List<UsuarioDTO> usuarioListTest;

  @BeforeEach
  void setUp() {
    rolTest = new Rol(1L, "ADMIN");
    rolTest2 = new Rol(2L, "USUARIO");

    rolListTest = Arrays.asList(rolTest, rolTest2);

    usuarioTest = new UsuarioDTO(1L, 1L, "usuarioTest", "correo@test.com");
    usuarioTest2 = new UsuarioDTO(2L, 2L, "usuaioTest2", "2correo@test.com");
    usuarioListTest = Arrays.asList(usuarioTest, usuarioTest2);
  }

  @Test
  void testObtenerListaRoles_JsonListAndOK() throws Exception {
    when(service.listarRoles()).thenReturn(rolListTest);
    
    mockMvc.perform(get("/api/roles"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].idRol").value(1L))
            .andExpect(jsonPath("$[0].tipoRol").value("ADMIN"))
            .andExpect(jsonPath("$[1].idRol").value(2L))
            .andExpect(jsonPath("$[1].tipoRol").value("USUARIO"));
  }

  @Test
  void testBuscarRolPorId_JsonAndOK() throws Exception {
    when(service.obtenerRolPorId(1L)).thenReturn(rolTest);

    mockMvc.perform(get("/api/roles/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idRol").value(1L))
            .andExpect(jsonPath("$.tipoRol").value("ADMIN"));
  }

  @Test
  void testObtenerUsuriosPorRolId_JsonListAndOK() throws Exception {
    when(service.obtenerUsuariosPorRol(1L)).thenReturn(usuarioListTest);

    mockMvc.perform(get("/api/roles/1/usuarios"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1L))
            .andExpect(jsonPath("$[0].nombreUsuario").value("usuarioTest"));
  }

  @Test
  void testCrearRolNuevo_JsonAndCreated() throws Exception{
    when(service.guardarRol(rolTest)).thenReturn(rolTest);

    mockMvc.perform(post("/api/roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(rolTest)))   
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.idRol").value(1L))
            .andExpect(jsonPath("$.tipoRol").value("ADMIN"));
  }

  @Test
  void testActualizarRolPorId_JsonAndOK() throws Exception {
    Rol nuevaInfo = new Rol(1L, "test");
    when(service.actualizarRol(1L, nuevaInfo)).thenReturn(nuevaInfo);

    mockMvc.perform(put("/api/roles/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(nuevaInfo)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idRol").value(1L))
            .andExpect(jsonPath("$.tipoRol").value("test"));
  }

  @Test
  void testBorrarRolPorId_NoContent() throws Exception {
    when(service.borrarRol(1L)).thenReturn("Rol eliminado correctamente");

    mockMvc.perform(delete("/api/roles/1"))
            .andExpect(status().isNoContent());
  }
}
