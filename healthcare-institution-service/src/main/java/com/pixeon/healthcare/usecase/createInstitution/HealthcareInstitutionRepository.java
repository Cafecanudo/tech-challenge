package com.pixeon.healthcare.usecase.createInstitution;

import com.pixeon.healthcare.domain.entities.HealthcareInstitution;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthcareInstitutionRepository extends CrudRepository<HealthcareInstitution, Integer> {

}
