package com.microservicio.login.microservicio_login.controller;

import com.microservicio.login.microservicio_login.dto.LoginDTO;
import com.microservicio.login.microservicio_login.dto.RecuperarPasswordDTO;
import com.microservicio.login.microservicio_login.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
@Tag(name = "Autenticación", description = "API para autenticación y gestión de sesiones en ConnectForo")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario con correo y contraseña")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Autenticación exitosa",
            content = @Content(mediaType = "text/plain", 
            examples = @ExampleObject(value = "Autenticación exitosa"))),
        @ApiResponse(responseCode = "401", description = "Credenciales inválidas",
            content = @Content(mediaType = "text/plain", 
            examples = @ExampleObject(value = "Credenciales inválidas")))
    })
    @PostMapping
    public ResponseEntity<String> login(@RequestBody @Valid LoginDTO loginDTO) {
        Boolean autenticado = loginService.iniciarSesion(loginDTO).block();
        
        if (autenticado != null && autenticado) {
            return ResponseEntity.ok("Autenticación exitosa");
        } else {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }

    @Operation(summary = "Recuperar contraseña", description = "Envía un correo con instrucciones para recuperar la contraseña")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Correo de recuperación enviado exitosamente",
            content = @Content(mediaType = "text/plain", 
            examples = @ExampleObject(value = "Se ha enviado un correo con las instrucciones para recuperar la contraseña"))),
        @ApiResponse(responseCode = "400", description = "Error al enviar correo de recuperación",
            content = @Content(mediaType = "text/plain", 
            examples = @ExampleObject(value = "Error: Usuario no encontrado")))
    })
    @PostMapping("/recuperar-password")
    public ResponseEntity<String> recuperarPassword(@RequestBody @Valid RecuperarPasswordDTO recuperarPasswordDTO) {
        String resultado = loginService.recuperarPassword(recuperarPasswordDTO).block();
        
        if (resultado != null && !resultado.startsWith("Error:")) {
            return ResponseEntity.ok(resultado);
        } else {
            return ResponseEntity.badRequest().body(resultado);
        }
    }
} 