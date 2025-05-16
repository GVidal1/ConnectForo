package com.example.Registro.clients;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class UsuarioClient {
    
  private final WebClient webClient;

  
  public UsuarioClient(@Value("${usuarios-service.url}") String usuariosClientUrl) {
      this.webClient = WebClient.builder().baseUrl(usuariosClientUrl).build();
  }

  
  @SuppressWarnings("unchecked")
  public Map<String, Object> obtenerUsuarioPorId(Long id) {
      return this.webClient.get()
          .uri("/{id}", id)
          .retrieve()
          .onStatus(status -> status.is4xxClientError(), response -> response.bodyToMono(String.class).map(body -> new RuntimeException("Usuario no encontrado")))
          .bodyToMono(Map.class).block();
          }
}
