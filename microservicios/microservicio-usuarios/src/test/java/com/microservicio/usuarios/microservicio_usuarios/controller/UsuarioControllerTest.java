package com.microservicio.usuarios.microservicio_usuarios.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

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
import org.springframework.security.test.context.support.WithMockUser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicio.usuarios.microservicio_usuarios.dto.CambiarPasswordDTO;
import com.microservicio.usuarios.microservicio_usuarios.dto.LoginDTO;
import com.microservicio.usuarios.microservicio_usuarios.model.Usuarios;
import com.microservicio.usuarios.microservicio_usuarios.service.UserService;

@WebMvcTest(UserController.class)
public class UsuarioControllerTest {

  @MockBean
  private UserService service;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  //Inicalizacion de varibales globales
  private Usuarios usuarioTest;
  private List<Usuarios> usuarioListTest;

  @BeforeEach
  void setUp() {
    usuarioTest = new Usuarios(1L, 1L, "nombreTest", "correo@test.com", "passwordTest", LocalDateTime.now(), "gabriel", "vidal" );
    usuarioListTest = Arrays.asList(usuarioTest);
  }

  // GET - /api/usuarios
  @Test
  @WithMockUser
  void testListarUsuarios_JsonList_OK() throws Exception {
    when(service.listarUsuarios()).thenReturn(usuarioListTest);

    mockMvc.perform(get("/api/usuarios"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(usuarioTest.getId()))
            .andExpect(jsonPath("$[0].nombreUsuario").value(usuarioTest.getNombreUsuario()));
  }

  // GET - /api/usuarios/{id}
  @Test
  @WithMockUser
  void testObtenerUsuarioPorId_JsonAndOK() throws Exception {
    when(service.obtenerUsuarioPorId(1L)).thenReturn(usuarioTest);

    mockMvc.perform(get("/api/usuarios/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1));
  }

  // GET - /api/usuarios/rol/{id}
  @Test
  @WithMockUser
  void testObtenerUsuariosPorRol_JsonListAndOK() throws Exception{
    when(service.obtenerUsuariosPorRol(1L)).thenReturn(usuarioListTest);

    mockMvc.perform(get("/api/usuarios/rol/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1));
  }

  // POST - /api/usuarios
  @Test
  @WithMockUser
  void testCrearNuevoUsuario_JsonAndCreated() throws Exception {
    // UsuarioDTO usuarioNuevo = new UsuarioDTO(1L , "NuevoUsuario", "nuevo@correo.com", "testing");

    when(service.guardarUsuario(any(Usuarios.class))).thenReturn(usuarioTest);

    mockMvc.perform(post("/api/usuarios")
            .with(csrf()) // <-- para simular el token (Cross-Site Request Forgery)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(usuarioTest)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1));
  }

  // POST - /api/usuarios/login
  @Test
  @WithMockUser
  void testLoginUsuario_StringAndOK() throws Exception {
    LoginDTO login = new LoginDTO("correo@test.com", "testing");
    when(service.autenticarUsuario("correo@test.com", "testing")).thenReturn(true);

    mockMvc.perform(post("/api/usuarios/login")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(login)))
            .andExpect(status().isOk())
            .andExpect(content().string("Login exitoso"));
  }

  // DELETE - /api/usuarios/{id}
  @Test
  @WithMockUser
  void testEliminarUsuarioPorId_NoContent() throws Exception {
    when(service.borrarUsuario(1L)).thenReturn("Se ha eliminado el usuario correctamente.");

    mockMvc.perform(delete("/api/usuarios/1")
            .with(csrf()))
            .andExpect(status().isNoContent());
  }

  // GET - /api/usuarios/buscar?nombre=nombre&correo=correo
  @Test
  @WithMockUser
  void testBuscarUsuariosPorNombreOCorreo_JsonListAndOK() throws Exception {
    when(service.buscarUsuarios("Test", "test")).thenReturn(usuarioListTest);

    mockMvc.perform(get("/api/usuarios/buscar?nombre=Test&correo=test"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1));
  }

  // PUT - /api/usuarios/{idUsuario}/cambiar-password
  @Test
  @WithMockUser
  void testCambiarPasswordUsuario_JsonAndOK() throws Exception {
    CambiarPasswordDTO cambioPassword = new CambiarPasswordDTO( "passwordTest", "cambioTest", "cambioTest");
    when(service.cambiarPassword(1L, cambioPassword)).thenReturn(usuarioTest);

    mockMvc.perform(put("/api/usuarios/1/cambiar-password")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(cambioPassword)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1));
  }

  // POST - /api/usuarios/recuperar-password
  @Test
  @WithMockUser
  void testRecurperarPasswordUsuarioPorCorreo_StringAndOK() throws Exception {
    when(service.obtenerUsuarioPorCorreo("correo@test.com")).thenReturn(usuarioTest);

    mockMvc.perform(post("/api/usuarios/recuperar-password")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(usuarioTest)))
            .andExpect(status().isOk())
            .andExpect(content().string("Se ha enviado un correo con las instrucciones para recuperar la contraseña"));
  }
}
