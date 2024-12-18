package com.purchasing.health.repositories;

import com.purchasing.health.models.DataSetEncoded;
import com.purchasing.health.models.Dataset;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface DataSetEncodedRepository extends CrudRepository<DataSetEncoded,Long>{
    Optional<DataSetEncoded> findByIdorgnitAndDatasetidAndYearAndMonth(Long idorgnit, Dataset datasetid, Integer year, String month);
}
