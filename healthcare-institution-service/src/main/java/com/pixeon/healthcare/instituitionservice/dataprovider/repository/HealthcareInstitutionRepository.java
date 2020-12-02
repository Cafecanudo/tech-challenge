package com.pixeon.healthcare.instituitionservice.dataprovider.repository;

import com.pixeon.healthcare.instituitionservice.config.entity.HealthcareInstitution;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthcareInstitutionRepository extends CrudRepository<HealthcareInstitution, Integer> {

}
