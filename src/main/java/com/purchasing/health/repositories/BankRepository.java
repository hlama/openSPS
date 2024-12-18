package com.purchasing.health.repositories;

import com.purchasing.health.models.Bank;
import org.springframework.data.repository.CrudRepository;

public interface BankRepository extends CrudRepository<Bank,Long> {

    Bank findByCodename(String codename);
    Bank findByName(String name);
}
