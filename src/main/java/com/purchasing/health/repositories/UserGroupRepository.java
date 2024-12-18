package com.purchasing.health.repositories;


import java.util.List;

import com.purchasing.health.models.UserGroup;
import org.springframework.data.repository.Repository;

public interface UserGroupRepository  extends Repository<UserGroup,Long>{
    
    List<UserGroup> findAll();
    UserGroup findByLibelle(String libelle);
    UserGroup findById(Long id);

}
