package com.microservicio.categorias.microservicio_categorias.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservicio.categorias.microservicio_categorias.dto.ForosDTO;

@FeignClient(name = "foros", url = "localhost:8085/api/foros")
public interface ForosClient {

  @GetMapping("/categorias/{idCategoria}")
  List<ForosDTO> listarForosPorCategoria(@PathVariable long idCategoria);
  
}
