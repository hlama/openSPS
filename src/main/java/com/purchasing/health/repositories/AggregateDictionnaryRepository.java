package com.purchasing.health.repositories;

import com.purchasing.health.models.AggregateDictionnary;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AggregateDictionnaryRepository extends CrudRepository<AggregateDictionnary,Long> {
    Optional<AggregateDictionnary> findByCodeContaining(String formula);
}
