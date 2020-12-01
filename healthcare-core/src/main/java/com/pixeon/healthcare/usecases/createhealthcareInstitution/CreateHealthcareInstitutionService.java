package com.pixeon.healthcare.usecases.createhealthcareInstitution;

import com.pixeon.healthcare.domain.models.HealthcareInstitutionDTO;
import com.pixeon.healthcare.usecases.createhealthcareInstitution.exception.CNPJEmptyException;
import com.pixeon.healthcare.usecases.createhealthcareInstitution.exception.CNPJInvalidException;
import com.pixeon.healthcare.usecases.createhealthcareInstitution.exception.NameCantEmptyException;
import com.pixeon.healthcare.usecases.getvalueconfigapplication.ApplicationConfigFactory;

public class CreateHealthcareInstitutionService {

    private HealthcareInstitutionFactory healthcareInstitutionFactory;
    private ApplicationConfigFactory applicationConfigFactory;

    public CreateHealthcareInstitutionService(ApplicationConfigFactory applicationConfigFactory, HealthcareInstitutionFactory healthcareInstitutionFactory) {
        this.applicationConfigFactory = applicationConfigFactory;
        this.healthcareInstitutionFactory = healthcareInstitutionFactory;
    }

    public HealthcareInstitutionDTO create(HealthcareInstitutionDTO healthcareInstitutionDTO) {
        validName(healthcareInstitutionDTO);
        validCNPJ(healthcareInstitutionDTO);

        healthcareInstitutionDTO.setCoins(applicationConfigFactory.getValueForNewHealthcareInstitution());
        return healthcareInstitutionFactory.save(healthcareInstitutionDTO);
    }

    private void validName(HealthcareInstitutionDTO healthcareInstitutionDTO) {
        if (healthcareInstitutionDTO.isEmptyOrBlank()) {
            throw new NameCantEmptyException();
        }
    }

    private void validCNPJ(HealthcareInstitutionDTO healthcareInstitutionDTO) {
        if (healthcareInstitutionDTO.isEmptyOrBlankCNPJ()) {
            throw new CNPJEmptyException();
        }

        if (!healthcareInstitutionDTO.isValidCNPJ()) {
            throw new CNPJInvalidException();
        }
    }
}
