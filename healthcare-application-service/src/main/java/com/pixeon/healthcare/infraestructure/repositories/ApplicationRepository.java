package com.pixeon.healthcare.infraestructure.repositories;

import com.pixeon.healthcare.domain.entities.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends CrudRepository<Configuration, Integer> {

}
