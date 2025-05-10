package com.example.USER.controller;

@RestController
@RequestMapping("api/v1")
public class UserController {
    
    @Autowired
    private UserService UserService;

    @GetMapping("/users")
    public ResponseEntity<List<Usuario>> listaDeUsuarios() {
        List<Usuario> usuarios = usuariosService.listarUsuarios();

        if(usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(usuarios);
    }

    @PostMapping("/users")
    public ResponseEntity<?> crearUsuario(@RequestParam String userName, @RequestParam String password, @RequestParam String correo) {
        try {
            Usuario newUser = usuariosService.crearUsuario(userName, password, correo);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }




}
