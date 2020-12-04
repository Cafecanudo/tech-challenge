package com.pixeon.healthcare.domain.usecase.updatebalanceofinstitution.impl;

import com.pixeon.healthcare.domain.config.exception.NegativeValuesNotAcceptedException;
import com.pixeon.healthcare.domain.config.exception.NoBalanceToCreateExamException;
import com.pixeon.healthcare.domain.model.CoinModel;
import com.pixeon.healthcare.domain.model.HealthcareInstitutionModel;
import com.pixeon.healthcare.domain.model.OperationModel;
import com.pixeon.healthcare.domain.usecase.createexam.CheckBalanceInstitution;
import com.pixeon.healthcare.domain.usecase.updatebalanceofinstitution.UpdateBalanceOfInstitutionGateway;
import com.pixeon.healthcare.domain.usecase.updatebalanceofinstitution.UpdateBalanceOfInstitutionUseCase;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
public class UpdateBalanceOfInstitutionUseCaseImpl implements UpdateBalanceOfInstitutionUseCase {

    private UpdateBalanceOfInstitutionGateway updateBalanceOfInstitutionGateway;

    @Override
    public HealthcareInstitutionModel update(OperationModel operationModel) {
        HealthcareInstitutionModel healthcareInstitution = updateBalanceOfInstitutionGateway.getHealthcareInstitutionById(operationModel.getHealthcareInstitutionId());
        checkIfInstitutionHaveEnoughBalance(healthcareInstitution, operationModel);
        checkNegativeValues(operationModel);

        CoinModel coin = chargeValueForConsultingExam(healthcareInstitution, operationModel);
        healthcareInstitution.setCoin(coin.getNewBalance());
        updateBalanceOfInstitutionGateway.createCoinFromOperation(coin);
        return updateBalanceOfInstitutionGateway.update(healthcareInstitution);
    }

    private CoinModel chargeValueForConsultingExam(HealthcareInstitutionModel institution, OperationModel operationModel) {
        return CoinModel.builder()
                .dateOperation(new Date())
                .currentBalance(institution.getCoin())
                .newBalance(calculateValue(institution, operationModel))
                .operation(operationModel.getOperation())
                .valueOperation(operationModel.getValue())
                .healthcareInstitution(institution)
                .build();
    }

    private void checkNegativeValues(OperationModel operationModel) {
        if (operationModel.isNegative()) {
            throw new NegativeValuesNotAcceptedException();
        }
    }

    private BigDecimal calculateValue(HealthcareInstitutionModel institution, OperationModel operationModel) {
        if (operationModel.isDebit()) {
            return institution.subtractCoins(operationModel.getValue());
        }
        return institution.additionsCoins(operationModel.getValue());
    }

    private void checkIfInstitutionHaveEnoughBalance(HealthcareInstitutionModel currentInstitution, OperationModel operationModel) {
        if (operationModel.isDebit()) {
            new CheckBalanceInstitution<>(new NoBalanceToCreateExamException()).check(currentInstitution, operationModel.getValue());
        }
    }
}
