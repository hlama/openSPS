package com.purchasing.health.repositories;

import com.purchasing.health.models.Dataset;
import com.purchasing.health.models.TypeOrgunit;
import org.springframework.data.repository.CrudRepository;


public interface DataSetRepository extends CrudRepository<Dataset,Long> {
    Dataset findByDatasetname(String datasetname);
    Iterable<Dataset >findByTypeOrgunit(TypeOrgunit typeOrgunit);
}
