package com.example.ingresar.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.ingresar.model.LoginModel;
import com.example.ingresar.model.RegistroModel;

import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class RegistroClient {

    private final WebClient webClient;

    public RegistroClient(@Value("${usuarios-service.url}") String registroServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(registroServiceUrl)
                .build();
    }

    public RegistroModel getRegistroPorId(Long id) {
    return this.webClient.get()
        .uri("/{id}", id)
        .retrieve()
        .onStatus(status -> status.is4xxClientError(), response ->
            response.bodyToMono(String.class)
                .flatMap(errorBody -> Mono.error(new RuntimeException("Error 4xx: " + errorBody))))
        .onStatus(status -> status.is5xxServerError(), response ->
            response.bodyToMono(String.class)
                .flatMap(errorBody -> Mono.error(new RuntimeException("Error 5xx: " + errorBody))))
        .bodyToMono(RegistroModel.class)
        .block();
}

}



    
    


