package com.pixeon.healthcare.domain.usecase.createhealthcareinstitution;

import com.pixeon.healthcare.domain.model.HealthcareInstitutionModel;

public interface HealthcareInstitutionGateway {

    HealthcareInstitutionModel save(HealthcareInstitutionModel healthcareInstitutionModel);

    HealthcareInstitutionModel getCurrentInstitution();

    HealthcareInstitutionModel getInstitutionForExamBy(Integer examId);

    void update(HealthcareInstitutionModel institution);
}
