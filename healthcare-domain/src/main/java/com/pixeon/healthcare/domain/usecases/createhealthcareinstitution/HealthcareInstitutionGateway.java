package com.pixeon.healthcare.domain.usecases.createhealthcareinstitution;

import com.pixeon.healthcare.domain.entities.HealthcareInstitution;

public interface HealthcareInstitutionGateway {

    HealthcareInstitution save(HealthcareInstitution healthcareInstitution);

    HealthcareInstitution getCurrentInstitution();

    HealthcareInstitution getInstitutionForExamBy(Integer examId);

    void update(HealthcareInstitution institution);
}
