package com.pixeon.healthcare.domain.usecase.getExamById.impl;

import com.pixeon.healthcare.domain.config.exception.InstitutionDoesNotOwnExamException;
import com.pixeon.healthcare.domain.config.exception.NoBalanceToConsultingExamException;
import com.pixeon.healthcare.domain.entity.Exam;
import com.pixeon.healthcare.domain.entity.HealthcareInstitution;
import com.pixeon.healthcare.domain.usecase.createexam.CheckBalanceInstitution;
import com.pixeon.healthcare.domain.usecase.createexam.ExamGateway;
import com.pixeon.healthcare.domain.usecase.createhealthcareinstitution.HealthcareInstitutionGateway;
import com.pixeon.healthcare.domain.usecase.getExamById.GetExamById;
import com.pixeon.healthcare.domain.usecase.getvalueconfigapplication.ApplicationConfigGateway;

import java.math.BigDecimal;

public class GetExamByIdImpl implements GetExamById {

    private ExamGateway examGateway;
    private ApplicationConfigGateway applicationConfigGateway;
    private HealthcareInstitutionGateway healthcareInstitutionGateway;

    public GetExamByIdImpl(ApplicationConfigGateway applicationConfigGateway, ExamGateway examGateway, HealthcareInstitutionGateway healthcareInstitutionGateway) {
        this.applicationConfigGateway = applicationConfigGateway;
        this.examGateway = examGateway;
        this.healthcareInstitutionGateway = healthcareInstitutionGateway;
    }

    public Exam get(int examId) {
        BigDecimal valueForConsultingExam = applicationConfigGateway.getValueForConsultingExam();
        HealthcareInstitution currentInstitution = healthcareInstitutionGateway.getCurrentInstitution();

        Exam exam = examGateway.getExameById(examId);
        chargeCaseFirstTime(valueForConsultingExam, currentInstitution, exam);
        exam.setHealthcareInstitution(currentInstitution);
        exam.setBilled(true);
        return exam;
    }

    private void chargeCaseFirstTime(BigDecimal valueForConsultingExam, HealthcareInstitution currentInstitution, Exam exam) {
        if (!exam.alreadyBeenCharged()) {
            checkIfInstitutionOwnsExam(currentInstitution, exam.getHealthcareInstitution());
            checkIfInstitutionHaveEnoughBalance(valueForConsultingExam, currentInstitution);
            chargeValueForConsultingExam(valueForConsultingExam, currentInstitution);
            updateInstitutionAfterConsultingExam(currentInstitution, exam);
        }
    }

    private void checkIfInstitutionOwnsExam(HealthcareInstitution currentInstitution, HealthcareInstitution examInstitution) {
        if (!currentInstitution.equals(examInstitution)) {
            throw new InstitutionDoesNotOwnExamException();
        }
    }

    private void checkIfInstitutionHaveEnoughBalance(BigDecimal valueForConsultingExam, HealthcareInstitution currentInstitution) {
        new CheckBalanceInstitution<>(new NoBalanceToConsultingExamException()).check(currentInstitution, valueForConsultingExam);
    }

    private void updateInstitutionAfterConsultingExam(HealthcareInstitution institution, Exam exam) {
        healthcareInstitutionGateway.update(institution);
    }

    private void chargeValueForConsultingExam(BigDecimal valueForConsultingExam, HealthcareInstitution institution) {
        institution.subtractCoins(valueForConsultingExam);
    }

}
