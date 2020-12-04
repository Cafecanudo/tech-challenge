package com.pixeon.healthcare.domain.usecase.createexam.impl;

import com.pixeon.healthcare.domain.config.exception.CreateExamFieldEmptyException;
import com.pixeon.healthcare.domain.config.exception.NoBalanceToCreateExamException;
import com.pixeon.healthcare.domain.model.CoinModel;
import com.pixeon.healthcare.domain.model.ExamModel;
import com.pixeon.healthcare.domain.model.HealthcareInstitutionModel;
import com.pixeon.healthcare.domain.model.enums.OperationEnum;
import com.pixeon.healthcare.domain.usecase.createexam.CheckBalanceInstitution;
import com.pixeon.healthcare.domain.usecase.createexam.CreateExamUsecase;
import com.pixeon.healthcare.domain.usecase.createexam.ExamGateway;
import com.pixeon.healthcare.domain.usecase.createhealthcareinstitution.HealthcareInstitutionGateway;
import com.pixeon.healthcare.domain.usecase.getvalueconfigapplication.ApplicationConfigGateway;

import java.math.BigDecimal;
import java.util.Date;

public class CreateExamUsecaseImpl implements CreateExamUsecase {

    private ExamGateway examGateway;
    private ApplicationConfigGateway applicationConfigGateway;
    private HealthcareInstitutionGateway healthcareInstitutionGateway;

    public CreateExamUsecaseImpl(ExamGateway examGateway, HealthcareInstitutionGateway healthcareInstitutionGateway,
                                 ApplicationConfigGateway applicationConfigGateway) {
        this.examGateway = examGateway;
        this.healthcareInstitutionGateway = healthcareInstitutionGateway;
        this.applicationConfigGateway = applicationConfigGateway;
    }

    public ExamModel create(ExamModel examModel) {
        cleanField(examModel);

        BigDecimal valueCreateExam = applicationConfigGateway.getValueCreateExam();
        HealthcareInstitutionModel institution = healthcareInstitutionGateway.getCurrentInstitution();
        examModel.setHealthcareInstitution(institution);

        validExamField(examModel);
        checkIfInstitutionHaveEnoughBalance(valueCreateExam, institution);
        CoinModel newBalance = chargeValueForConsultingExam(valueCreateExam, institution);
        updateBalanceInstitutionAfterConsultingExam(institution, newBalance);

        return examGateway.save(examModel);
    }

    private void cleanField(ExamModel examModel) {
        examModel.setId(null);
        examModel.setBilled(false);
    }

    private void validExamField(ExamModel examModel) {
        if (examModel.isEmptyFields()) {
            throw new CreateExamFieldEmptyException();
        }
    }

    private void checkIfInstitutionHaveEnoughBalance(BigDecimal valueCreateExam, HealthcareInstitutionModel currentInstitution) {
        new CheckBalanceInstitution<>(new NoBalanceToCreateExamException()).check(currentInstitution, valueCreateExam);
    }

    private void updateBalanceInstitutionAfterConsultingExam(HealthcareInstitutionModel institution, CoinModel coinModel) {
        institution.setCoin(coinModel.getNewBalance());
        healthcareInstitutionGateway.updateBalance(institution, coinModel);
    }

    private CoinModel chargeValueForConsultingExam(BigDecimal valueCreateExam, HealthcareInstitutionModel institution) {
        return CoinModel.builder()
                .dateOperation(new Date())
                .currentBalance(institution.getCoin())
                .newBalance(institution.subtractCoins(valueCreateExam))
                .operation(OperationEnum.DEBIT)
                .valueOperation(valueCreateExam)
                .build();
    }
}
