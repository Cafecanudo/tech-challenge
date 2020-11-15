package com.pixeon.healthcare.usecases.createhealthcareInstitution;

import com.pixeon.healthcare.domain.models.HealthcareInstitution;

public interface HealthcareInstitutionService {

    HealthcareInstitution save(HealthcareInstitution healthcareInstitution);

    HealthcareInstitution getCurrentInstitution();

    HealthcareInstitution getInstitutionForExamBy(Integer examId);

    void update(HealthcareInstitution institution);
}
