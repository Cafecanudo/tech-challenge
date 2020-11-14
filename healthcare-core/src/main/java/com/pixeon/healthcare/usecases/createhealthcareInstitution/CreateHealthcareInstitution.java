package com.pixeon.healthcare.usecases.createhealthcareInstitution;

import com.pixeon.healthcare.models.HealthcareInstitution;
import com.pixeon.healthcare.usecases.createhealthcareInstitution.exception.CNPJEmptyException;
import com.pixeon.healthcare.usecases.createhealthcareInstitution.exception.CNPJInvalidException;
import com.pixeon.healthcare.usecases.createhealthcareInstitution.exception.NameCantEmptyException;

public class CreateHealthcareInstitution {

    private HealthcareInstitutionService healthcareInstitutionService;

    public CreateHealthcareInstitution(HealthcareInstitutionService healthcareInstitutionService) {
        this.healthcareInstitutionService = healthcareInstitutionService;
    }

    public HealthcareInstitution create(HealthcareInstitution healthcareInstitution) {
        validName(healthcareInstitution);
        validCNPJ(healthcareInstitution);

        healthcareInstitution.setCoins(healthcareInstitutionService.getValueForNewInstitution());
        return healthcareInstitutionService.save(healthcareInstitution);
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
