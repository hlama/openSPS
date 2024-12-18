package com.purchasing.health.repositories;

import com.purchasing.health.models.Calculation;
import com.purchasing.health.models.Dataset;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CalculationRepository extends CrudRepository<Calculation,Long> {

    Calculation findByDatasetidAndFormula(Dataset dataset, String formula);
    Calculation findByDatasetidAndStartdateAndEnddate(Dataset dataset,Long startdate,Long enddate);
    Optional<Calculation> findByDatasetidAndStatus(Dataset dataset, Integer status);
}
