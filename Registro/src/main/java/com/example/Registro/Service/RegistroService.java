package com.example.Registro.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Registro.Model.RegistroModel;
import com.example.Registro.Repository.RegistroRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RegistroService {

    @Autowired
    private RegistroRepository registroRepository;


    public RegistroModel registrarUsuario(RegistroModel registro) {
        return registroRepository.save(registro);
    }

    public RegistroModel buscarRegistro(Long id) {
        return registroRepository.findById(id).orElseThrow(() -> new RuntimeException("Registro no encontrado"));
    }

}








// public Comentarios guardarComentario(Comentarios comentarioNuevo) {
//     Map<String, Object> verificarPost = postClient.obtenerPostPorId(comentarioNuevo.getIdPost());
//     Map<String, Object> verificarUsuario = usuarioClient.obtenerUsuarioPorId(comentarioNuevo.getIdUsuario());

//     if (verificarPost == null || verificarPost.isEmpty()) {
//       throw new RuntimeException("El id de la publicación no se ha encontrado. No se puede crear un comentario.");
//     }

//     if (verificarUsuario == null || verificarUsuario.isEmpty()) {
//       throw new RuntimeException("El id del usuario no existe. No es psible realizar un comentario");
//     }

//     return comentariosRepository.save(comentarioNuevo);
    
//   }


