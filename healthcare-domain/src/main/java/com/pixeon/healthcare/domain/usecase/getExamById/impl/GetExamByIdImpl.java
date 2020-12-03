package com.pixeon.healthcare.domain.usecase.getExamById.impl;

import com.pixeon.healthcare.domain.config.exception.InstitutionDoesNotOwnExamException;
import com.pixeon.healthcare.domain.config.exception.NoBalanceToConsultingExamException;
import com.pixeon.healthcare.domain.model.CoinModel;
import com.pixeon.healthcare.domain.model.ExamModel;
import com.pixeon.healthcare.domain.model.HealthcareInstitutionModel;
import com.pixeon.healthcare.domain.model.enums.OperationEnum;
import com.pixeon.healthcare.domain.usecase.createexam.CheckBalanceInstitution;
import com.pixeon.healthcare.domain.usecase.createexam.ExamGateway;
import com.pixeon.healthcare.domain.usecase.createhealthcareinstitution.HealthcareInstitutionGateway;
import com.pixeon.healthcare.domain.usecase.getExamById.GetExamById;
import com.pixeon.healthcare.domain.usecase.getvalueconfigapplication.ApplicationConfigGateway;

import java.math.BigDecimal;
import java.util.Date;

public class GetExamByIdImpl implements GetExamById {

    private ExamGateway examGateway;
    private ApplicationConfigGateway applicationConfigGateway;
    private HealthcareInstitutionGateway healthcareInstitutionGateway;

    public GetExamByIdImpl(ApplicationConfigGateway applicationConfigGateway, ExamGateway examGateway, HealthcareInstitutionGateway healthcareInstitutionGateway) {
        this.applicationConfigGateway = applicationConfigGateway;
        this.examGateway = examGateway;
        this.healthcareInstitutionGateway = healthcareInstitutionGateway;
    }

    public ExamModel get(int examId) {
        BigDecimal valueForConsultingExam = applicationConfigGateway.getValueForConsultingExam();
        HealthcareInstitutionModel currentInstitution = healthcareInstitutionGateway.getCurrentInstitution();

        ExamModel examModel = examGateway.getExameById(examId);
        chargeCaseFirstTime(valueForConsultingExam, currentInstitution, examModel);

        examModel.setHealthcareInstitution(currentInstitution);
        examModel.setBilled(true);
        return examModel;
    }

    private void chargeCaseFirstTime(BigDecimal valueForConsultingExam, HealthcareInstitutionModel currentInstitution, ExamModel examModel) {
        if (!examModel.alreadyBeenCharged()) {
            checkIfInstitutionOwnsExam(currentInstitution, examModel.getHealthcareInstitution());
            checkIfInstitutionHaveEnoughBalance(valueForConsultingExam, currentInstitution);
            CoinModel newBalance = chargeValueForConsultingExam(valueForConsultingExam, currentInstitution);
            updateInstitutionAfterConsultingExam(currentInstitution, newBalance);
        }
    }

    private void checkIfInstitutionOwnsExam(HealthcareInstitutionModel currentInstitution, HealthcareInstitutionModel examInstitution) {
        if (!currentInstitution.equals(examInstitution)) {
            throw new InstitutionDoesNotOwnExamException();
        }
    }

    private void checkIfInstitutionHaveEnoughBalance(BigDecimal valueForConsultingExam, HealthcareInstitutionModel currentInstitution) {
        new CheckBalanceInstitution<>(new NoBalanceToConsultingExamException()).check(currentInstitution, valueForConsultingExam);
    }

    private void updateInstitutionAfterConsultingExam(HealthcareInstitutionModel institution, CoinModel coinModel) {
        institution.setCoin(coinModel.getNewBalance());
        healthcareInstitutionGateway.updateBalance(institution, coinModel);
    }

    private CoinModel chargeValueForConsultingExam(BigDecimal valueForConsultingExam, HealthcareInstitutionModel institution) {
        return CoinModel.builder()
                .dateOperation(new Date())
                .currentBalance(institution.getCoin())
                .newBalance(institution.subtractCoins(valueForConsultingExam))
                .operation(OperationEnum.DEBIT)
                .build();
    }

}
