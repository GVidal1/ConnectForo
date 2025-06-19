package com.microservicio.usuarios.microservicio_usuarios.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UserController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping()
    public ResponseEntity<List<Usuarios>> listaDeUsuarios() {
        List<Usuarios> usuarios = userService.listarUsuarios();

        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(usuarios);
    }

    // GET - /api/usuarios/{idUsuario}
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
    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            Usuarios usuario = userService.obtenerUsuarioPorCorreo(loginDTO.getCorreo());

            if (passwordEncoder.matches(loginDTO.getPassword(), usuario.getPassword())) {
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

    // GET - /api/usuarios/{idUsuario}/estadisticas
    @GetMapping("/{idUsuario}/estadisticas")
    public ResponseEntity<?> obtenerEstadisticasUsuario(@PathVariable Long idUsuario) {
        try {
            Map<String, Object> estadisticas = userService.obtenerEstadisticasUsuario(idUsuario);
            return ResponseEntity.ok(estadisticas);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // POST - /api/usuarios/recuperar-password
    // http://localhost:8083/api/usuarios/recuperar-password
    // {
    //     "correo": "ejemplo@correo.com"
    //   }
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