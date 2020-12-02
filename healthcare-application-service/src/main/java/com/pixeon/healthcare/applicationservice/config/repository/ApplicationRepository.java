package com.pixeon.healthcare.applicationservice.config.repository;

import com.pixeon.healthcare.applicationservice.config.repository.entity.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends CrudRepository<Configuration, Integer> {

}
