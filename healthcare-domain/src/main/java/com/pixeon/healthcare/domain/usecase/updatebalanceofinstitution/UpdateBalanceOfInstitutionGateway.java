package com.pixeon.healthcare.domain.usecase.updatebalanceofinstitution;

import com.pixeon.healthcare.domain.model.CoinModel;
import com.pixeon.healthcare.domain.model.HealthcareInstitutionModel;

public interface UpdateBalanceOfInstitutionGateway {

    HealthcareInstitutionModel getHealthcareInstitutionById(Integer healthcareInstitutionId);

    void createCoinFromOperation(CoinModel coin);

    HealthcareInstitutionModel update(HealthcareInstitutionModel healthcareInstitution);
}
