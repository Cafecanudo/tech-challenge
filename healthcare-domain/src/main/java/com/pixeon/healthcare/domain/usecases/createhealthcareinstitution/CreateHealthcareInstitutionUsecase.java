package com.pixeon.healthcare.domain.usecases.createhealthcareinstitution;

import com.pixeon.healthcare.domain.entities.HealthcareInstitution;

@FunctionalInterface
public interface CreateHealthcareInstitutionUsecase {

    HealthcareInstitution create(HealthcareInstitution healthcareInstitution);
}
