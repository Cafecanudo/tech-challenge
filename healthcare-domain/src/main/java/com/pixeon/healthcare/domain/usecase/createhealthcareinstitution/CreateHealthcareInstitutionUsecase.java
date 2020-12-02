package com.pixeon.healthcare.domain.usecase.createhealthcareinstitution;

import com.pixeon.healthcare.domain.entity.HealthcareInstitution;

@FunctionalInterface
public interface CreateHealthcareInstitutionUsecase {

    HealthcareInstitution create(HealthcareInstitution healthcareInstitution);
}
