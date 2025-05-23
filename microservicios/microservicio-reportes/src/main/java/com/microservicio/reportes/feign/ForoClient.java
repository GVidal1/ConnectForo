package com.microservicio.reportes.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "foros", url = "http://localhost:8085/api/foros")
public interface ForoClient {
    
    @GetMapping("/{id}")
    Object getForoById(@PathVariable Long id);
} 