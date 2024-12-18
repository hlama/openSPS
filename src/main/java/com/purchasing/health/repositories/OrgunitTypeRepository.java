package com.purchasing.health.repositories;

import com.purchasing.health.models.TypeOrgunit;
import org.springframework.data.repository.CrudRepository;

public interface OrgunitTypeRepository extends CrudRepository<TypeOrgunit,Long> {
    
    TypeOrgunit findByLibelle(String libelle);
}
