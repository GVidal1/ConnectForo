package com.microservicio.registro.microservicio_registro.service;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.microservicio.registro.microservicio_registro.clients.UsuarioClient;
import com.microservicio.registro.microservicio_registro.dto.UsuarioDTO;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class RegistroServiceTest {

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private RegistroService registroService;

    private UsuarioDTO usuarioTest;

    @BeforeEach
    void setUp() {
        usuarioTest = new UsuarioDTO(1L, "usuarioTest", "correo@test.com", "password123");
    }

    @Test
    void testRegistrarUsuario_Exitoso() {
        when(usuarioClient.sincronizarUsuario(usuarioTest)).thenReturn(Mono.just(true));

        UsuarioDTO resultado = registroService.registrarUsuario(usuarioTest);

        assertThat(resultado).isEqualTo(usuarioTest);
        assertThat(resultado.getIdRol()).isEqualTo(1L);
        assertThat(resultado.getNombreUsuario()).isEqualTo("usuarioTest");
        assertThat(resultado.getCorreo()).isEqualTo("correo@test.com");
        assertThat(resultado.getPassword()).isEqualTo("password123");
    }

    @Test
    void testRegistrarUsuario_Fallido() {
        when(usuarioClient.sincronizarUsuario(usuarioTest)).thenReturn(Mono.just(false));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            registroService.registrarUsuario(usuarioTest);
        });

        assertThat(exception.getMessage()).isEqualTo("No se pudo registrar el usuario en el microservicio de Usuarios.");

        //Otra Forma de hacerlo tiene el error y la captura en solo 1
        // assertThatThrownBy(() -> registroService.registrarUsuario(usuarioTest))
        //         .isInstanceOf(RuntimeException.class)
        //         .hasMessage("No se pudo registrar el usuario en el microservicio de Usuarios.");
    }

    @Test
    void testRegistrarUsuario_ErrorEnCliente() {
        when(usuarioClient.sincronizarUsuario(usuarioTest))
                .thenReturn(Mono.error(new RuntimeException("Error de conexión")));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            registroService.registrarUsuario(usuarioTest);
        });

        assertThat(exception.getMessage()).isEqualTo("Error de conexión");
    }
} 