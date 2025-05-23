package com.microservicio.reportes.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "categorias", url = "http://localhost:8085/api/categorias")
public interface CategoriaClient {
    
    @GetMapping("/{id}")
    Object getCategoriaById(@PathVariable Long id);
} 