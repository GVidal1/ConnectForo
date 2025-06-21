package com.microservicio.login.microservicio_login.clients;

import com.microservicio.login.microservicio_login.dto.LoginDTO;
import com.microservicio.login.microservicio_login.dto.RecuperarPasswordDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Component
public class UsuarioClient {

    private final WebClient webClient;

    public UsuarioClient(@Value("${usuarios-service.url}") String usuarioServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(usuarioServiceUrl)
                .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public Mono<Boolean> verificarCredenciales(LoginDTO loginDTO) {
        return webClient.post()
                .uri("/api/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(loginDTO)
                .retrieve()
                .toBodilessEntity()
                .map(response -> response.getStatusCode().is2xxSuccessful())
                .onErrorResume(e -> {
                    if (e instanceof WebClientResponseException) {
                        WebClientResponseException ex = (WebClientResponseException) e;
                        if (ex.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                            return Mono.just(false);
                        }
                    }
                    System.err.println("Error al verificar credenciales: " + e.getMessage());
                    return Mono.just(false);
                });
    }

    public Mono<String> recuperarPassword(RecuperarPasswordDTO recuperarPasswordDTO) {
        return webClient.post()
                .uri("/api/usuarios/recuperar-password")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(recuperarPasswordDTO)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(e -> {
                    if (e instanceof WebClientResponseException) {
                        WebClientResponseException ex = (WebClientResponseException) e;
                        if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
                            return Mono.just("Error: " + ex.getResponseBodyAsString());
                        }
                    }
                    System.err.println("Error al recuperar contraseña: " + e.getMessage());
                    return Mono.just("Error interno del servidor");
                });
    }
} 