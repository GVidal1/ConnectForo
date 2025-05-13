package com.example.USER.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.USER.model.Usuarios;
import com.example.USER.repository.UsuarioRepository;

@Service
public class UserService {

    @Autowired
<<<<<<< HEAD
    private UsuarioRepository usuarioRepository;
=======
        private UsuarioRepository usuarioRepository;
>>>>>>> 0d39f00d78eadc7b2f9f3ad3e912f5053ad9f81d

    public List<Usuarios> listarUsuarios() {
        return usuarioRepository.findAll();
    }

<<<<<<< HEAD
    public Usuarios crearUsuario(String user, String pass, String corr) {
        Usuarios user1 = new Usuarios();
        user1.setNombreUsario(user);
        user1.setPassword(pass);
        user1.setCorreo(corr);
        return usuarioRepository.save(user1);
=======
    public Usuarios obtenerUsuarioPorId(Long idUsuario) {
        return usuarioRepository.findById(idUsuario).orElseThrow(() -> new RuntimeException("No se ha encontrado en usuario con ese ID."));
>>>>>>> 0d39f00d78eadc7b2f9f3ad3e912f5053ad9f81d
    }

    
    public String borrarUsuario(Long id) {
        Usuarios usuarioAct = obtenerUsuarioPorId(id);
        usuarioRepository.deleteById(usuarioAct.getId());
        return "Se ha eliminado el usuario correctamente.";
    }
    
    public Usuarios guardarUsuario(Usuarios nuevoUsuario) {
        usuarioRepository.save(nuevoUsuario);
        return nuevoUsuario;
    }


    // antiguo metodo que ocuapa el RequestParam
    // public Usuarios crearUsuario(String user, String pass, String corr) {
    //     Usuarios user1 = new Usuarios();
    //     user1.setNombreUsuario(user);
    //     user1.setPassword(pass);
    //     user1.setCorreo(corr);
    //     return usuarioRepository.save(user1);
    // }

    public Usuarios actualizarUsuario(Long idUsuario, Usuarios nuevaInfo){
        Usuarios usuarioActual = obtenerUsuarioPorId(idUsuario);

        // Verifica que los campos no esten vacios (No es necesario el id ya que los toma del metodo obtenerUsuariosPorId ni la fecha de creacion)
        if (nuevaInfo.getNombreUsuario() != null) {
        usuarioActual.setNombreUsuario(nuevaInfo.getNombreUsuario());
        }

        if (nuevaInfo.getPassword() != null) {
            usuarioActual.setPassword(nuevaInfo.getPassword());
        }

        if (nuevaInfo.getCorreo() != null) {
            usuarioActual.setCorreo(nuevaInfo.getCorreo());
        }

        return usuarioRepository.save(usuarioActual);
    }
}
