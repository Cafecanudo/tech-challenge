package com.pixeon.healthcare.domain.usecases.createexam.impl;

import com.pixeon.healthcare.domain.config.exception.CreateExamFieldEmptyException;
import com.pixeon.healthcare.domain.config.exception.NoBalanceToCreateExamException;
import com.pixeon.healthcare.domain.entities.Exam;
import com.pixeon.healthcare.domain.entities.HealthcareInstitution;
import com.pixeon.healthcare.domain.usecases.createexam.CheckBalanceInstitution;
import com.pixeon.healthcare.domain.usecases.createexam.CreateExamUsecase;
import com.pixeon.healthcare.domain.usecases.createexam.ExamGateway;
import com.pixeon.healthcare.domain.usecases.createhealthcareinstitution.HealthcareInstitutionGateway;
import com.pixeon.healthcare.domain.usecases.getvalueconfigapplication.ApplicationConfigGateway;

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

    public Exam create(Exam exam) {
        BigDecimal valueCreateExam = applicationConfigGateway.getValueCreateExam();
        HealthcareInstitution institution = healthcareInstitutionGateway.getCurrentInstitution();
        exam.setHealthcareInstitution(institution);

        validExamField(exam);
        checkIfInstitutionHaveEnoughBalance(valueCreateExam, institution);
        chargeValueForConsultingExam(valueCreateExam, institution);
        updateInstitutionAfterConsultingExam(institution, exam);

        return examGateway.save(exam);
    }

    private void validExamField(Exam exam) {
        if (exam.isEmptyFields()) {
            throw new CreateExamFieldEmptyException();
        }
    }

    private void checkIfInstitutionHaveEnoughBalance(BigDecimal valueCreateExam, HealthcareInstitution currentInstitution) {
        new CheckBalanceInstitution<>(new NoBalanceToCreateExamException()).check(currentInstitution, valueCreateExam);
    }

    private void updateInstitutionAfterConsultingExam(HealthcareInstitution institution, Exam exam) {
        healthcareInstitutionGateway.update(institution);
    }

    private void chargeValueForConsultingExam(BigDecimal valueCreateExam, HealthcareInstitution institution) {
        institution.subtractCoins(valueCreateExam);
    }

}
