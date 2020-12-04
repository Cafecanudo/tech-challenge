package com.pixeon.healthcare.domain.usecase.createhealthcareinstitution.impl;

import com.pixeon.healthcare.domain.config.exception.CNPJEmptyException;
import com.pixeon.healthcare.domain.config.exception.CNPJInvalidException;
import com.pixeon.healthcare.domain.config.exception.NameCantEmptyException;
import com.pixeon.healthcare.domain.model.CoinModel;
import com.pixeon.healthcare.domain.model.HealthcareInstitutionModel;
import com.pixeon.healthcare.domain.model.enums.OperationEnum;
import com.pixeon.healthcare.domain.usecase.createhealthcareinstitution.CreateHealthcareInstitutionUsecase;
import com.pixeon.healthcare.domain.usecase.createhealthcareinstitution.HealthcareInstitutionGateway;
import com.pixeon.healthcare.domain.usecase.getvalueconfigapplication.ApplicationConfigGateway;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

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

        createCoinForNewInstitution(healthcareInstitutionModel);
        return healthcareInstitutionGateway.save(healthcareInstitutionModel);
    }

    private void createCoinForNewInstitution(HealthcareInstitutionModel healthcareInstitutionModel) {
        CoinModel coin = CoinModel.builder().operation(OperationEnum.CREDIT).currentBalance(new BigDecimal(0))
                .newBalance(applicationConfigGateway.getValueForNewHealthcareInstitution())
                .dateOperation(new Date()).build();

        healthcareInstitutionModel.setCoins(Arrays.asList(coin));
        healthcareInstitutionModel.setCoin(coin.getNewBalance());
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
