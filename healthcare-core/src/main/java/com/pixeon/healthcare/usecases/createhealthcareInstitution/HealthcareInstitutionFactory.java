package com.pixeon.healthcare.usecases.createhealthcareInstitution;

import com.pixeon.healthcare.domain.models.HealthcareInstitutionDTO;

public interface HealthcareInstitutionFactory {

    HealthcareInstitutionDTO save(HealthcareInstitutionDTO healthcareInstitutionDTO);

    HealthcareInstitutionDTO getCurrentInstitution();

    HealthcareInstitutionDTO getInstitutionForExamBy(Integer examId);

    void update(HealthcareInstitutionDTO institution);
}
