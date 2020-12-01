package com.pixeon.healthcare.usecases.getExamById;

import com.pixeon.healthcare.domain.exception.InstitutionDoesNotOwnExamException;
import com.pixeon.healthcare.domain.models.ExamModel;
import com.pixeon.healthcare.domain.models.HealthcareInstitutionDTO;
import com.pixeon.healthcare.usecases.createexam.CheckBalanceInstitution;
import com.pixeon.healthcare.usecases.createexam.ExamService;
import com.pixeon.healthcare.usecases.createhealthcareInstitution.HealthcareInstitutionFactory;
import com.pixeon.healthcare.usecases.getExamById.exception.NoBalanceToConsultingExamException;
import com.pixeon.healthcare.usecases.getvalueconfigapplication.ApplicationConfigFactory;

import java.math.BigDecimal;

public class GetExamById {

    private ExamService examService;
    private ApplicationConfigFactory applicationConfigFactory;
    private HealthcareInstitutionFactory institutionService;

    public GetExamById(ApplicationConfigFactory applicationConfigFactory, ExamService examService, HealthcareInstitutionFactory institutionService) {
        this.applicationConfigFactory = applicationConfigFactory;
        this.examService = examService;
        this.institutionService = institutionService;
    }

    public ExamModel get(int examId) {
        BigDecimal valueForConsultingExam = applicationConfigFactory.getValueForConsultingExam();
        HealthcareInstitutionDTO currentInstitution = institutionService.getCurrentInstitution();

        ExamModel exam = examService.getExameById(examId);
        if (!exam.isBilled()) {
            checkIfInstitutionOwnsExam(currentInstitution, exam.getHealthcareInstitution());
            checkIfInstitutionHaveEnoughBalance(valueForConsultingExam, currentInstitution);
            chargeValueForConsultingExam(valueForConsultingExam, currentInstitution);
            updateInstitutionAfterConsultingExam(currentInstitution, exam);
        }
        exam.setHealthcareInstitution(currentInstitution);
        exam.setBilled(true);
        return exam;
    }

    private void checkIfInstitutionOwnsExam(HealthcareInstitutionDTO currentInstitution, HealthcareInstitutionDTO examInstitution) {
        if (!currentInstitution.equals(examInstitution)) {
            throw new InstitutionDoesNotOwnExamException();
        }
    }

    private void checkIfInstitutionHaveEnoughBalance(BigDecimal valueForConsultingExam, HealthcareInstitutionDTO currentInstitution) {
        new CheckBalanceInstitution<>(new NoBalanceToConsultingExamException()).check(currentInstitution, valueForConsultingExam);
    }

    private void updateInstitutionAfterConsultingExam(HealthcareInstitutionDTO institution, ExamModel exam) {
        institutionService.update(institution);
    }

    private void chargeValueForConsultingExam(BigDecimal valueForConsultingExam, HealthcareInstitutionDTO institution) {
        institution.subtractCoins(valueForConsultingExam);
    }
}
