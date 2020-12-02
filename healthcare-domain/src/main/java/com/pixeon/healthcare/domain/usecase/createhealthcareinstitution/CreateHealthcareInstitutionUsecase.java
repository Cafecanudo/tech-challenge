package com.pixeon.healthcare.domain.usecase.createhealthcareinstitution;

import com.pixeon.healthcare.domain.model.HealthcareInstitutionModel;

@FunctionalInterface
public interface CreateHealthcareInstitutionUsecase {

    HealthcareInstitutionModel create(HealthcareInstitutionModel healthcareInstitutionModel);
}
