package com.purchasing.health.repositories;

import com.purchasing.health.models.AireSante;
import com.purchasing.health.models.Provinces;
import com.purchasing.health.models.ZoneSante;
import org.springframework.data.repository.CrudRepository;

public interface AireSanteRepository extends CrudRepository<AireSante,Long> {
    AireSante findByDhis2id(String dhis2id);
    Iterable<ZoneSante> findByIdProvinces(Provinces idProvinces);

    Iterable<AireSante> findByZoneSante(ZoneSante zoneSante);
}
