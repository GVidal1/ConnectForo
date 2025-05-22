package com.example.ingresar.clients;

import java.time.Duration;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.ingresar.dto.UsuarioResponseDTO;

import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Component
public class RegistroClient {
    private final WebClient webClient;

    public RegistroClient(@Value("${usuarios-service.url}") String registroServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(registroServiceUrl)
                .clientConnector(new ReactorClientHttpConnector(
                    HttpClient.create()
                        .responseTimeout(Duration.ofSeconds(5))
                ))
                .build();
    }

    public UsuarioResponseDTO getUsuarioPorId(Long id) {
        return this.webClient.get()
            .uri("/{id}", id)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
            .map(this::convertirMapADTO)
            .block();
    }

    public UsuarioResponseDTO getUsuarioPorNickname(String nickname) {
        return this.webClient.get()
            .uri("/buscar?nickname={nickname}", nickname)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
            .map(this::convertirMapADTO)
            .block();
    }

    private UsuarioResponseDTO convertirMapADTO(Map<String, Object> map) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(map.get("id") instanceof Integer ? 
                 ((Integer) map.get("id")).longValue() : 
                 (Long) map.get("id"));
        dto.setNombreUsuario((String) map.get("nombreUsuario"));
        dto.setCorreo((String) map.get("correo"));
        return dto;
    }
}
    
    


