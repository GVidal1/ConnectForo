package com.microservicio.roles.microservicio_roles.clients;

import com.microservicio.roles.microservicio_roles.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
public class UsuarioClient {

    private final WebClient webClient;

    public UsuarioClient(@Value("${usuarios-service.url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public UsuarioDTO obtenerUsuarioPorId(Long idUsuario) {
        return webClient.get()
                .uri("/{idUsuario}", idUsuario)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), 
                    response -> response.bodyToMono(String.class)
                        .map(body -> new RuntimeException("Usuario no encontrado")))
                .bodyToMono(UsuarioDTO.class)
                .block();
    }

    public List<UsuarioDTO> obtenerTodosLosUsuarios() {
        UsuarioDTO[] usuarios = webClient.get()
                .uri("")
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), 
                    response -> response.bodyToMono(String.class)
                        .map(body -> new RuntimeException("Error al obtener usuarios")))
                .bodyToMono(UsuarioDTO[].class)
                .block();
        return Arrays.asList(usuarios);
    }

    public List<UsuarioDTO> obtenerUsuariosPorRol(Long idRol) {
        UsuarioDTO[] usuarios = webClient.get()
                .uri("/rol/{idRol}", idRol)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), 
                    response -> response.bodyToMono(String.class)
                        .map(body -> new RuntimeException("Error al obtener usuarios por rol")))
                .bodyToMono(UsuarioDTO[].class)
                .block();
        return Arrays.asList(usuarios);
    }
} 