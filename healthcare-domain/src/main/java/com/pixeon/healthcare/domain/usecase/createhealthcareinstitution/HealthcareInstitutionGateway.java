package com.pixeon.healthcare.domain.usecase.createhealthcareinstitution;

import com.pixeon.healthcare.domain.model.CoinModel;
import com.pixeon.healthcare.domain.model.HealthcareInstitutionModel;

public interface HealthcareInstitutionGateway {

    default HealthcareInstitutionModel save(HealthcareInstitutionModel healthcareInstitutionModel) {
        throw new RuntimeException("Not implemented");
    }

    default HealthcareInstitutionModel getCurrentInstitution() {
        throw new RuntimeException("Not implemented");
    }

    default HealthcareInstitutionModel getInstitutionForExamBy(Integer examId) {
        throw new RuntimeException("Not implemented");
    }

    default void updateBalance(HealthcareInstitutionModel institution, CoinModel coin) {
        throw new RuntimeException("Not implemented");
    }

    default HealthcareInstitutionModel getInstitutionById(Integer healthcareInstituitionId) {
        throw new RuntimeException("Not implemented");
    }
}
