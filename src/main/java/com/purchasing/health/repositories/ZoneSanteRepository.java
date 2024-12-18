package com.purchasing.health.repositories;

import com.purchasing.health.models.Provinces;
import com.purchasing.health.models.ZoneSante;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface ZoneSanteRepository extends CrudRepository<ZoneSante,Long> {
    
    ZoneSante findByDhis2id(String dhis2id);
    Iterable<ZoneSante> findByIdProvinces(Provinces idProvinces);
    Optional<ZoneSante> findByIdAndName(Long id, String name);
}
