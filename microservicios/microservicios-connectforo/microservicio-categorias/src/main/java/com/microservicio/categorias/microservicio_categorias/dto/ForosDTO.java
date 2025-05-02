package com.microservicio.categorias.microservicio_categorias.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ForosDTO {

  Long id;
  String titulo;
  String descripcion;
  LocalDateTime fechaCreacion;
}
