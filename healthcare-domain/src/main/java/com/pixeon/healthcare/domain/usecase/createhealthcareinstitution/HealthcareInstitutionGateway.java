package com.pixeon.healthcare.domain.usecase.createhealthcareinstitution;

import com.pixeon.healthcare.domain.entity.HealthcareInstitution;

public interface HealthcareInstitutionGateway {

    HealthcareInstitution save(HealthcareInstitution healthcareInstitution);

    HealthcareInstitution getCurrentInstitution();

    HealthcareInstitution getInstitutionForExamBy(Integer examId);

    void update(HealthcareInstitution institution);
}
