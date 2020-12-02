package com.pixeon.healthcare.domain.usecase.createhealthcareinstitution.impl;

import com.pixeon.healthcare.domain.config.exception.CNPJEmptyException;
import com.pixeon.healthcare.domain.config.exception.CNPJInvalidException;
import com.pixeon.healthcare.domain.config.exception.NameCantEmptyException;
import com.pixeon.healthcare.domain.model.HealthcareInstitutionModel;
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

    public HealthcareInstitutionModel create(HealthcareInstitutionModel healthcareInstitutionModel) {
        validName(healthcareInstitutionModel);
        validCNPJ(healthcareInstitutionModel);

        healthcareInstitutionModel.setCoin(applicationConfigGateway.getValueForNewHealthcareInstitution());
        return healthcareInstitutionGateway.save(healthcareInstitutionModel);
    }

    private void validName(HealthcareInstitutionModel healthcareInstitutionModel) {
        if (healthcareInstitutionModel.isEmptyOrBlank()) {
            throw new NameCantEmptyException();
        }
    }

    private void validCNPJ(HealthcareInstitutionModel healthcareInstitutionModel) {
        if (healthcareInstitutionModel.isEmptyOrBlankCNPJ()) {
            throw new CNPJEmptyException();
        }

        if (!healthcareInstitutionModel.isValidCNPJ()) {
            throw new CNPJInvalidException();
        }
    }
}
