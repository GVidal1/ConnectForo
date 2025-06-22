package com.microservicio.usuarios.microservicio_usuarios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.usuarios.microservicio_usuarios.dto.CambiarPasswordDTO;
import com.microservicio.usuarios.microservicio_usuarios.dto.LoginDTO;
import com.microservicio.usuarios.microservicio_usuarios.dto.RecuperarPasswordDTO;
import com.microservicio.usuarios.microservicio_usuarios.dto.UsuarioDTO;
import com.microservicio.usuarios.microservicio_usuarios.model.Usuarios;
import com.microservicio.usuarios.microservicio_usuarios.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "API para gestión de usuarios en ConnectForo")
public class UserController {
    
    @Autowired
    private UserService userService;

    @Operation(summary = "Obtener lista de usuarios", description = "Retorna una lista de todos los usuarios registrados en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Usuarios.class))),
        @ApiResponse(responseCode = "204", description = "No hay usuarios registrados")
    })
    @GetMapping()
    public ResponseEntity<List<Usuarios>> listaDeUsuarios() {
        List<Usuarios> usuarios = userService.listarUsuarios();

        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(usuarios);
    }

    // GET - /api/usuarios/{idUsuario}
    @Operation(summary = "Buscar usuario por ID", description = "Obtiene la información de un usuario específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Usuarios.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
            content = @Content(mediaType = "text/plain", 
            examples = @ExampleObject(value = "Usuario no encontrado")))
    })
    @GetMapping("/{idUsuario}")
    public ResponseEntity<?> buscarUsuarioPorId(@PathVariable Long idUsuario) {
        try {
            Usuarios usuario = userService.obtenerUsuarioPorId(idUsuario);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // GET - /api/usuarios/rol/{idRol}
    @Operation(summary = "Buscar usuarios por rol", description = "Obtiene una lista de usuarios que pertenecen a un rol específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuarios encontrados exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Usuarios.class))),
        @ApiResponse(responseCode = "204", description = "No hay usuarios con ese rol"),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    @GetMapping("/rol/{idRol}")
    public ResponseEntity<?> buscarUsuariosPorRol(@PathVariable Long idRol) {
        try {
            List<Usuarios> usuarios = userService.obtenerUsuariosPorRol(idRol);
            if (usuarios.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(usuarios);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    // POST - /api/usuarios
    // http://localhost:8083/api/usuarios
    //     {
    //     "idRol": 1,
    //     "nombreUsuario": "ejemplo",
    //     "correo": "ejemplo@correo.com",
    //     "password": "miclave123"
    //   }

    @Operation(summary = "Crear nuevo usuario", description = "Crea un nuevo usuario en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Usuarios.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o correo ya en uso",
            content = @Content(mediaType = "text/plain", 
            examples = @ExampleObject(value = "El correo ya está en uso.")))
    })
    @PostMapping
    public ResponseEntity<?> crearUsuarioDesdeDTO(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        try {
            Usuarios usuario = new Usuarios();
            usuario.setIdRol(usuarioDTO.getIdRol());
            usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());
            usuario.setCorreo(usuarioDTO.getCorreo());
            usuario.setPassword(usuarioDTO.getPassword());

            Usuarios usuarioGuardado = userService.guardarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioGuardado);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("El correo ya está en uso.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error al crear el usuario: " + e.getMessage());
        }
    }

    // POST - /api/usuarios/login
    // http://localhost:8083/api/usuarios/login
    // {
    //     "correo": "ejemplo@correo.com",
    //     "password": "miclave123"
    //   }
    @Operation(summary = "Autenticar usuario", description = "Autentica un usuario con correo y contraseña")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login exitoso",
            content = @Content(mediaType = "text/plain", 
            examples = @ExampleObject(value = "Login exitoso"))),
        @ApiResponse(responseCode = "401", description = "Credenciales inválidas",
            content = @Content(mediaType = "text/plain", 
            examples = @ExampleObject(value = "Contraseña incorrecta")))
    })
    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            boolean autenticado = userService.autenticarUsuario(loginDTO.getCorreo(), loginDTO.getPassword());
            if (autenticado) {
                return ResponseEntity.ok("Login exitoso");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado");
        }
    }

    // PUT - /api/usuarios/{idUsuario}
    // http://localhost:8083/api/usuarios/{idUsuario}
    // {
    //     "id": 1,
    //     "idRol": 2,
    //     "nombreUsuario": "nuevoNombre",
    //     "correo": "nuevo@correo.com",
    //     "password": "nuevaClave",
    //     "fechaCreacion": "2024-06-01T00:00:00",
    //     "nombre": "Nombre",
    //     "apellidos": "Apellido"
    //   }
    @Operation(summary = "Actualizar información de usuario", description = "Actualiza la información de un usuario existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Usuarios.class))),
        @ApiResponse(responseCode = "400", description = "Error al actualizar usuario",
            content = @Content(mediaType = "text/plain", 
            examples = @ExampleObject(value = "Error al actualizar el usuario: Usuario no encontrado")))
    })
    @PutMapping("/{idUsuario}")
    public ResponseEntity<?> actualizarInformacionUsuario(
        @PathVariable Long idUsuario, 
        @RequestBody @Valid Usuarios nuevaInfoUsuario) {
        try {
            Usuarios usuarioActualzado = userService.actualizarUsuario(idUsuario, nuevaInfoUsuario);
            return ResponseEntity.ok(usuarioActualzado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error al actualizar el usuario: " + e.getMessage());
        }
    }

    // DELETE - /api/usuarios/{idUsuario}
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
            content = @Content(mediaType = "text/plain", 
            examples = @ExampleObject(value = "Usuario no encontrado")))
    })
    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<?> borrarUsuarioPorId(@PathVariable Long idUsuario) {
        try {
            String resultado = userService.borrarUsuario(idUsuario);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // GET - /api/usuarios/buscar?nombre=nombre&correo=correo
    @Operation(summary = "Buscar usuarios", description = "Busca usuarios por nombre y/o correo electrónico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuarios encontrados exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Usuarios.class))),
        @ApiResponse(responseCode = "204", description = "No se encontraron usuarios"),
        @ApiResponse(responseCode = "400", description = "Error en la búsqueda")
    })
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarUsuarios(
        @RequestParam(required = false) String nombre,
        @RequestParam(required = false) String correo) {
        try {
            List<Usuarios> usuarios = userService.buscarUsuarios(nombre, correo);
            if (usuarios.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(usuarios);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // PUT - /api/usuarios/{idUsuario}/cambiar-password
    // http://localhost:8083/api/usuarios/{idUsuario}/cambiar-password
    // {
    //     "passwordActual": "miclave123",
    //     "nuevaPassword": "nuevaClave456",
    //     "confirmarPassword": "nuevaClave456"
    //   }

    @Operation(summary = "Cambiar contraseña", description = "Permite a un usuario cambiar su contraseña")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contraseña cambiada exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Usuarios.class))),
        @ApiResponse(responseCode = "400", description = "Error al cambiar contraseña",
            content = @Content(mediaType = "text/plain", 
            examples = @ExampleObject(value = "La contraseña actual es incorrecta")))
    })
    @PutMapping("/{idUsuario}/cambiar-password")
    public ResponseEntity<?> cambiarPassword(
        @PathVariable Long idUsuario,
        @RequestBody @Valid CambiarPasswordDTO passwordDTO) {
        try {
            Usuarios usuario = userService.cambiarPassword(idUsuario, passwordDTO);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // POST - /api/usuarios/recuperar-password
    // http://localhost:8083/api/usuarios/recuperar-password
    // {
    //     "correo": "ejemplo@correo.com"
    //   }
    @Operation(summary = "Solicitar recuperación de contraseña", description = "Envía un correo con instrucciones para recuperar la contraseña")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Correo de recuperación enviado exitosamente",
            content = @Content(mediaType = "text/plain", 
        examples = @ExampleObject(value = "Se ha enviado un correo con las instrucciones para recuperar la contraseña"))),
        @ApiResponse(responseCode = "400", description = "Error al enviar correo de recuperación",
            content = @Content(mediaType = "text/plain", 
            examples = @ExampleObject(value = "Usuario no encontrado")))
    })
    @PostMapping("/recuperar-password")
    public ResponseEntity<?> solicitarRecuperacionPassword(@RequestBody @Valid RecuperarPasswordDTO dto) {
        try {
            userService.solicitarRecuperacionPassword(dto.getCorreo());
            return ResponseEntity.ok("Se ha enviado un correo con las instrucciones para recuperar la contraseña");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 