package com.microservicio.registro.microservicio_registro.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicio.registro.microservicio_registro.config.SecurityConfig;
import com.microservicio.registro.microservicio_registro.dto.UsuarioDTO;
import com.microservicio.registro.microservicio_registro.service.RegistroService;

@WebMvcTest(RegistroController.class)
@Import(SecurityConfig.class)
public class RegistroControllerTest {

  @MockBean
  private RegistroService service;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private UsuarioDTO usuarioTest;

  @BeforeEach 
  void setUp() {
    usuarioTest = new UsuarioDTO(1L , "usuarioTest", "correo@test.com", "passwordTest");
  }

  @Test
  void testRegistrarUsuario_Exitoso() throws Exception {
    when(service.registrarUsuario(usuarioTest)).thenReturn(usuarioTest);

    mockMvc.perform(post("/api/registro")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(usuarioTest)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.idRol").value(1L));
  }

  @Test
  void testRegistrarUsuario_Fallido() throws Exception {
    when(service.registrarUsuario(usuarioTest))
            .thenThrow(new RuntimeException("No se pudo registrar el usuario en el microservicio de Usuarios."));

    mockMvc.perform(post("/api/registro")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(usuarioTest)))
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$").value("Error al registrar: No se pudo registrar el usuario en el microservicio de Usuarios."));
  }

  @Test
  void testRegistrarUsuario_ErrorEnCliente() throws Exception {
    when(service.registrarUsuario(usuarioTest))
            .thenThrow(new RuntimeException("Error de conexión"));

    mockMvc.perform(post("/api/registro")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(usuarioTest)))
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$").value("Error al registrar: Error de conexión"));
  }
}
