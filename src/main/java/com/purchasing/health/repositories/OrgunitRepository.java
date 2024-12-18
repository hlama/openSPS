package com.purchasing.health.repositories;

import com.purchasing.health.models.AireSante;
import com.purchasing.health.models.Orgunits;
import com.purchasing.health.models.TypeOrgunit;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrgunitRepository extends CrudRepository<Orgunits,Long>{

    Orgunits findByDhis2id(String dhis2id);
    Iterable<Orgunits> findByTypeOrgunit(TypeOrgunit typeOrgunit);
    Iterable<Orgunits> findByTypeOrgunitAndAireSante(TypeOrgunit typeOrgunit, AireSante aireSante);
    Iterable<Orgunits> findByAireSante(AireSante aireSante);
    Optional<Orgunits> findByIdAndNameAndTypeOrgunit(Long id, String name,TypeOrgunit typeOrgunit);

}
