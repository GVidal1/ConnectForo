package com.microservicio.login.microservicio_login.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.microservicio.login.microservicio_login.clients.UsuarioClient;
import com.microservicio.login.microservicio_login.dto.LoginDTO;
import com.microservicio.login.microservicio_login.dto.RecuperarPasswordDTO;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

  @Mock
  private UsuarioClient usuarioClient;

  @InjectMocks
  private LoginService loginService;

  private LoginDTO loginDTO;
  private RecuperarPasswordDTO recuperarPasswordDTO;

  @BeforeEach
  void setUp() {
    loginDTO = new LoginDTO("correo@test.com", "passwordTest");
    recuperarPasswordDTO = new RecuperarPasswordDTO("correo@test.com");
  }

  @Test
  void testIniciarSesion_Exitosa() {
    when(usuarioClient.verificarCredenciales(loginDTO)).thenReturn(Mono.just(true));

    Mono<Boolean> result = loginService.iniciarSesion(loginDTO);

    assertThat(result.block()).isTrue();
  }

  @Test
  void testIniciarSesion_Fallida() {
    when(usuarioClient.verificarCredenciales(loginDTO)).thenReturn(Mono.just(false));

    Mono<Boolean> result = loginService.iniciarSesion(loginDTO);

    assertThat(result.block()).isFalse();
  }

  // error de conexión o servidor no disponible
  @Test
  void testIniciarSesion_ErrorServidor() {
    when(usuarioClient.verificarCredenciales(loginDTO))
        .thenReturn(Mono.error(new RuntimeException("Error de conexión al servidor")));

    Mono<Boolean> result = loginService.iniciarSesion(loginDTO);

    assertThat(result.block()).isNull();
  }

  // retorna false cuando hay un error
  @Test
  void testIniciarSesion_ErrorServidorManejado() {
    when(usuarioClient.verificarCredenciales(loginDTO))
        .thenReturn(Mono.just(false));

    Mono<Boolean> result = loginService.iniciarSesion(loginDTO);

    assertThat(result.block()).isFalse();
  }

  @Test
  void testRecuperarPassword_Exitoso() {
    String mensajeExito = "Se ha enviado un correo con las instrucciones para recuperar la contraseña";
    when(usuarioClient.recuperarPassword(recuperarPasswordDTO)).thenReturn(Mono.just(mensajeExito));

    Mono<String> result = loginService.recuperarPassword(recuperarPasswordDTO);

    assertThat(result.block()).isEqualTo(mensajeExito);
  }

  @Test
  void testRecuperarPassword_Error() {
    String mensajeError = "Error: Usuario no encontrado";
    when(usuarioClient.recuperarPassword(recuperarPasswordDTO)).thenReturn(Mono.just(mensajeError));

    Mono<String> result = loginService.recuperarPassword(recuperarPasswordDTO);

    assertThat(result.block()).isEqualTo(mensajeError);
  }
}
