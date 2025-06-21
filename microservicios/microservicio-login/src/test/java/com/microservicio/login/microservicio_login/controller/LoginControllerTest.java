package com.microservicio.login.microservicio_login.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
import com.microservicio.login.microservicio_login.config.SecurityConfig;
import com.microservicio.login.microservicio_login.dto.LoginDTO;
import com.microservicio.login.microservicio_login.dto.RecuperarPasswordDTO;
import com.microservicio.login.microservicio_login.service.LoginService;

import reactor.core.publisher.Mono;

@WebMvcTest(LoginController.class)
@Import(SecurityConfig.class)
public class LoginControllerTest {

  @MockBean
  private LoginService service;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private LoginDTO loginTest;
  private RecuperarPasswordDTO recuperarPasswordTest;

  @BeforeEach
  void setUp() {
    loginTest = new LoginDTO("correo@test.com", "passwordTest");
    recuperarPasswordTest = new RecuperarPasswordDTO("correo@test.com");
  }

  @Test
  void testLoginExitoso_StringAndOK() throws Exception {
    when(service.iniciarSesion(loginTest)).thenReturn(Mono.just(true));

    mockMvc.perform(post("/api/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginTest)))
            .andExpect(status().isOk())
            .andExpect(content().string("Autenticación exitosa"));
  }

  @Test
  void testLoginFallido_StringAndUnauthorized() throws Exception {
    when(service.iniciarSesion(loginTest)).thenReturn(Mono.just(false));

    mockMvc.perform(post("/api/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginTest)))
            .andExpect(status().isUnauthorized())
            .andExpect(content().string("Credenciales inválidas"));
  }

  @Test
  void testRecuperarPassword_Exitoso_StringAndOK() throws Exception {
    String mensajeExito = "Se ha enviado un correo con las instrucciones para recuperar la contraseña";
    when(service.recuperarPassword(recuperarPasswordTest)).thenReturn(Mono.just(mensajeExito));

    mockMvc.perform(post("/api/login/recuperar-password")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(recuperarPasswordTest)))
            .andExpect(status().isOk())
            .andExpect(content().string(mensajeExito));
  }

  @Test
  void testRecuperarPassword_Error_StringAndUnauthorized() throws Exception {
    String mensajeError = "Error: Usuario no encontrado";
    when(service.recuperarPassword(recuperarPasswordTest)).thenReturn(Mono.just(mensajeError));

    mockMvc.perform(post("/api/login/recuperar-password")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(recuperarPasswordTest)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(mensajeError));
  }
}
