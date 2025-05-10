package com.example.USER.service;

@Service
public class UserService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll();
    }

    public Usuario buscarUsuarioPorId(Long id){
        return usuarioRepository.findById(id).orElseThrow(()-> new RuntimeException("El usuario no ha sido encontrado"));

    }


    public Usuario crearUsuario(String user, String pass) {
        
        Usuario user1 = new Usuario();
        user1.setUserName(user);
        user1.setPassword(pass);
    

        return usuarioRepository.save(user1);
    }







}
