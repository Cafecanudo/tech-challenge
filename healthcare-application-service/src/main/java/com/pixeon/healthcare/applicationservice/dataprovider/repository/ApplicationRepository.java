package com.pixeon.healthcare.applicationservice.dataprovider.repository;

import com.pixeon.healthcare.applicationservice.config.entity.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends CrudRepository<Configuration, Integer> {

}
