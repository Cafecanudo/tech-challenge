package com.pixeon.healthcare.domain.usecase.createhealthcareinstitution.impl;

import com.pixeon.healthcare.domain.config.exception.CNPJEmptyException;
import com.pixeon.healthcare.domain.config.exception.CNPJInvalidException;
import com.pixeon.healthcare.domain.config.exception.NameCantEmptyException;
import com.pixeon.healthcare.domain.entity.HealthcareInstitution;
import com.pixeon.healthcare.domain.usecase.createhealthcareinstitution.CreateHealthcareInstitutionUsecase;
import com.pixeon.healthcare.domain.usecase.createhealthcareinstitution.HealthcareInstitutionGateway;
import com.pixeon.healthcare.domain.usecase.getvalueconfigapplication.ApplicationConfigGateway;

public class CreateHealthcareInstitutionUsecaseImpl implements CreateHealthcareInstitutionUsecase {

    private ApplicationConfigGateway applicationConfigGateway;
    private HealthcareInstitutionGateway healthcareInstitutionGateway;

    public CreateHealthcareInstitutionUsecaseImpl(ApplicationConfigGateway applicationConfigGateway, HealthcareInstitutionGateway healthcareInstitutionGateway) {
        this.applicationConfigGateway = applicationConfigGateway;
        this.healthcareInstitutionGateway = healthcareInstitutionGateway;
    }

    public HealthcareInstitution create(HealthcareInstitution healthcareInstitution) {
        validName(healthcareInstitution);
        validCNPJ(healthcareInstitution);

        healthcareInstitution.setCoin(applicationConfigGateway.getValueForNewHealthcareInstitution());
        return healthcareInstitutionGateway.save(healthcareInstitution);
    }

    private void validName(HealthcareInstitution healthcareInstitution) {
        if (healthcareInstitution.isEmptyOrBlank()) {
            throw new NameCantEmptyException();
        }
    }

    private void validCNPJ(HealthcareInstitution healthcareInstitution) {
        if (healthcareInstitution.isEmptyOrBlankCNPJ()) {
            throw new CNPJEmptyException();
        }

        if (!healthcareInstitution.isValidCNPJ()) {
            throw new CNPJInvalidException();
        }
    }
}
