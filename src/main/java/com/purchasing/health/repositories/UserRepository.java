package com.purchasing.health.repositories;

import com.purchasing.health.models.Users;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Users,Long> {
    Iterable<Users>findAll();
    
}
