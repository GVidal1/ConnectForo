package com.example.Registro.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.Registro.dto.UsuarioDTO;

import reactor.core.publisher.Mono;

@Component
public class UsuarioClient {

    private final WebClient webClient;

    public UsuarioClient(@Value("${usuarios-service.url}") String usuariosServiceUrl) {
        this.webClient = WebClient.builder()
            .baseUrl(usuariosServiceUrl)
            .build();
    }

    public Mono<Boolean> sincronizarUsuario(UsuarioDTO usuarioDTO) {
        return webClient.post()
            .uri("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(usuarioDTO)
            .retrieve()
            .onStatus(HttpStatusCode::isError, response -> 
                response.bodyToMono(String.class)
                    .flatMap(errorBody -> Mono.error(new RuntimeException(
                        "Error en el microservicio de Usuarios: " + response.statusCode() + " - " + errorBody
                    )))
            )
            .bodyToMono(Void.class)
            .thenReturn(true)
            .onErrorResume(e -> {
                System.err.println("Fallo al sincronizar usuario desde Registro: " + e.getMessage());
                return Mono.just(false);
            });
    }
}
