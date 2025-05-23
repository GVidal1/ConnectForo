package com.example.USER.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.USER.model.Usuarios;
import com.example.USER.repository.UsuarioRepository;

@Service
public class UserService {

    @Autowired
        private UsuarioRepository usuarioRepository;

    public List<Usuarios> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuarios obtenerUsuarioPorId(Long idUsuario) {
        return usuarioRepository.findById(idUsuario).orElseThrow(() -> new RuntimeException("No se ha encontrado en usuario con ese ID."));
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


    

    public Usuarios actualizarUsuario(Long idUsuario, Usuarios nuevaInfo){
        Usuarios usuarioActual = obtenerUsuarioPorId(idUsuario);

        
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
