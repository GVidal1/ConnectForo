package com.microservicio.comentarios.microservicio_comentarios.services;

import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    return comentariosRepository.findById(id).orElseThrow((() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "El id del comentario no se encontro en la base de datos.")));
  }

  public String borrarComentarioPorId(Long id) {
    Comentarios comentarioActual = buscarComentarioPorId(id);
    comentariosRepository.deleteById(comentarioActual.getId());
    return "Comentario Eliminado con existo";
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

  // Nuevos métodos de búsqueda
  public List<Comentarios> buscarPorPalabraEnContenido(String palabra) {
    return comentariosRepository.findByContenidoContainingIgnoreCase(palabra);
  }

  public List<Comentarios> buscarPorUsuario(Long idUsuario) {
    Map<String, Object> usuario = usuarioClient.obtenerUsuarioPorId(idUsuario);
    if (usuario == null || usuario.isEmpty()) {
      throw new RuntimeException("El usuario no existe");
    }
    return comentariosRepository.findByIdUsuario(idUsuario);
  }

  public List<Comentarios> buscarPorPost(Long idPost) {
    Map<String, Object> post = postClient.obtenerPostPorId(idPost);
    if (post == null || post.isEmpty()) {
      throw new RuntimeException("El post no existe");
    }
    return comentariosRepository.findByIdPost(idPost);
  }

  public List<Comentarios> buscarPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
    if (fechaInicio.isAfter(fechaFin)) {
      throw new RuntimeException("La fecha de inicio debe ser anterior a la fecha fin");
    }
    return comentariosRepository.findByFechaCreacionBetween(fechaInicio, fechaFin);
  }

  public List<Comentarios> buscarCreadosDespuesDe(LocalDateTime fecha) {
    return comentariosRepository.findByFechaCreacionAfter(fecha);
  }

  public List<Comentarios> buscarCreadosAntesDe(LocalDateTime fecha) {
    return comentariosRepository.findByFechaCreacionBefore(fecha);
  }

  public List<Comentarios> buscarPorLongitudContenido(int longitud) {
    return comentariosRepository.findByLongitudContenidoMayorA(longitud);
  }

  public List<Comentarios> buscarPorUsuarioYPost(Long idUsuario, Long idPost) {
    Map<String, Object> usuario = usuarioClient.obtenerUsuarioPorId(idUsuario);
    Map<String, Object> post = postClient.obtenerPostPorId(idPost);
    
    if (usuario == null || usuario.isEmpty()) {
      throw new RuntimeException("El usuario no existe");
    }
    if (post == null || post.isEmpty()) {
      throw new RuntimeException("El post no existe");
    }
    
    return comentariosRepository.findByIdUsuarioAndIdPost(idUsuario, idPost);
  }

  public Long contarComentariosPorPost(Long idPost) {
    Map<String, Object> post = postClient.obtenerPostPorId(idPost);
    if (post == null || post.isEmpty()) {
      throw new RuntimeException("El post no existe");
    }
    return comentariosRepository.countByIdPost(idPost);
  }

  public Long contarComentariosPorUsuario(Long idUsuario) {
    Map<String, Object> usuario = usuarioClient.obtenerUsuarioPorId(idUsuario);
    if (usuario == null || usuario.isEmpty()) {
      throw new RuntimeException("El usuario no existe");
    }
    return comentariosRepository.countByIdUsuario(idUsuario);
  }
}
