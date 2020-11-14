package com.pixeon.healthcare.usecases.createhealthcareInstitution;

import com.pixeon.healthcare.domain.models.HealthcareInstitution;

import java.math.BigDecimal;

public interface HealthcareInstitutionService {

    HealthcareInstitution save(HealthcareInstitution healthcareInstitution);

    HealthcareInstitution getCurrentInstitution();

    HealthcareInstitution getInstitutionForExamBy(Integer examId);

    BigDecimal getValueForNewInstitution();
}
