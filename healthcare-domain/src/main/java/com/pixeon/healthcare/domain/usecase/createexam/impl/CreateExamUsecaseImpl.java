package com.pixeon.healthcare.domain.usecase.createexam.impl;

import com.pixeon.healthcare.domain.config.exception.CreateExamFieldEmptyException;
import com.pixeon.healthcare.domain.config.exception.NoBalanceToCreateExamException;
import com.pixeon.healthcare.domain.model.ExamModel;
import com.pixeon.healthcare.domain.model.HealthcareInstitutionModel;
import com.pixeon.healthcare.domain.usecase.createexam.CheckBalanceInstitution;
import com.pixeon.healthcare.domain.usecase.createexam.CreateExamUsecase;
import com.pixeon.healthcare.domain.usecase.createexam.ExamGateway;
import com.pixeon.healthcare.domain.usecase.createhealthcareinstitution.HealthcareInstitutionGateway;
import com.pixeon.healthcare.domain.usecase.getvalueconfigapplication.ApplicationConfigGateway;

import java.math.BigDecimal;

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
        BigDecimal valueCreateExam = applicationConfigGateway.getValueCreateExam();
        HealthcareInstitutionModel institution = healthcareInstitutionGateway.getCurrentInstitution();
        examModel.setHealthcareInstitutionModel(institution);

        validExamField(examModel);
        checkIfInstitutionHaveEnoughBalance(valueCreateExam, institution);
        chargeValueForConsultingExam(valueCreateExam, institution);
        updateInstitutionAfterConsultingExam(institution, examModel);

        return examGateway.save(examModel);
    }

    private void validExamField(ExamModel examModel) {
        if (examModel.isEmptyFields()) {
            throw new CreateExamFieldEmptyException();
        }
    }

    private void checkIfInstitutionHaveEnoughBalance(BigDecimal valueCreateExam, HealthcareInstitutionModel currentInstitution) {
        new CheckBalanceInstitution<>(new NoBalanceToCreateExamException()).check(currentInstitution, valueCreateExam);
    }

    private void updateInstitutionAfterConsultingExam(HealthcareInstitutionModel institution, ExamModel examModel) {
        healthcareInstitutionGateway.update(institution);
    }

    private void chargeValueForConsultingExam(BigDecimal valueCreateExam, HealthcareInstitutionModel institution) {
        institution.subtractCoins(valueCreateExam);
    }

}
