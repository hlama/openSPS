package com.purchasing.health.api;

import com.purchasing.health.models.AireSante;
import com.purchasing.health.repositories.AireSanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/healtharea/")
public class AireSanteApi {

    @Autowired
    private AireSanteRepository aireSanteRepository;

    @GetMapping("list")
    public Iterable<AireSante> getHealthAreas(){
        return aireSanteRepository.findAll();
    }
}
