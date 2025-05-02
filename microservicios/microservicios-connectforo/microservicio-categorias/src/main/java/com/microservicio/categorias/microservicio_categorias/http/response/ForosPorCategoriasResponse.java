package com.microservicio.categorias.microservicio_categorias.http.response;

import java.time.LocalDateTime;
import java.util.List;

import com.microservicio.categorias.microservicio_categorias.dto.ForosDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ForosPorCategoriasResponse {
  Long id;
  String titulo;
  String descripcion;
  LocalDateTime fechaCreacion;
  List<ForosDTO> foros;
}
