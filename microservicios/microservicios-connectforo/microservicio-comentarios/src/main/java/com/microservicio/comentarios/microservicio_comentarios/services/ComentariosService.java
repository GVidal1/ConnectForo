package com.microservicio.comentarios.microservicio_comentarios.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicio.comentarios.microservicio_comentarios.clients.PostClient;
import com.microservicio.comentarios.microservicio_comentarios.clients.UsuarioClient;
import com.microservicio.comentarios.microservicio_comentarios.model.Comentarios;
import com.microservicio.comentarios.microservicio_comentarios.repository.ComentariosRepository;

@Service
public class ComentariosService {

  @Autowired
  private ComentariosRepository comentariosRepository;

  @Autowired
  private PostClient postClient;

  @Autowired
  private UsuarioClient usuarioClient;

  public List<Comentarios> listarComentarios() {
    return comentariosRepository.findAll();
  }

  public Comentarios buscarComentarioPorId(Long id) {
    return comentariosRepository.findById(id).orElseThrow((() -> new RuntimeException("El id del comentario no se encontro en la base de datos.")));
  }

  public void borrarComentarioPorId(Long id) {
    Comentarios comentarioActual = buscarComentarioPorId(id);
    comentariosRepository.deleteById(comentarioActual.getId());
  }

  public Comentarios guardarComentario(Comentarios comentarioNuevo) {
    Map<String, Object> verificarPost = postClient.obtenerPostPorId(comentarioNuevo.getIdPost());
    Map<String, Object> verificarUsuario = usuarioClient.obtenerUsuarioPorId(comentarioNuevo.getIdUsuario());

    if (verificarPost == null || verificarPost.isEmpty()) {
      throw new RuntimeException("El id de la publicación no se ha encontrado. No se puede crear un comentario.");
    }

    if (verificarUsuario == null || verificarUsuario.isEmpty()) {
      throw new RuntimeException("El id del usuario no existe. No es psible realizar un comentario");
    }

    return comentariosRepository.save(comentarioNuevo);
    
  }

  public Comentarios actualizarComentario(Long id, Comentarios comentarioActualizado) {
    Comentarios comentarioActual = buscarComentarioPorId(id);

    //LAS VARIABLE DE ID USUARIO Y POST NO SE PUEDEN CAMBIAR PORQUE ESTAN PROTEGIDAS
    // se actualiza el contenido del post no la informacion como ids

    if (comentarioActualizado.getContenido() != null) {
      comentarioActual.setContenido(comentarioActualizado.getContenido());
    }

    return comentariosRepository.save(comentarioActual);

  }

}
