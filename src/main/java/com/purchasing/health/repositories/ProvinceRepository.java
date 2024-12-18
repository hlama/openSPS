package com.purchasing.health.repositories;

import com.purchasing.health.models.Provinces;
import org.springframework.data.repository.CrudRepository;


public interface ProvinceRepository extends CrudRepository<Provinces,Long>{
    
    Provinces findByDhis2id(String dhis2id);
    Iterable<Provinces> findByActivity(String activity);
}
