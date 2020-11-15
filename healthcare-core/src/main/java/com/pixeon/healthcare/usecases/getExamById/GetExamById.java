package com.pixeon.healthcare.usecases.getExamById;

import com.pixeon.healthcare.domain.exception.InstitutionDoesNotOwnExamException;
import com.pixeon.healthcare.domain.models.ExamModel;
import com.pixeon.healthcare.domain.models.HealthcareInstitution;
import com.pixeon.healthcare.usecases.createexam.CheckBalanceInstitution;
import com.pixeon.healthcare.usecases.createexam.ExamService;
import com.pixeon.healthcare.usecases.createhealthcareInstitution.HealthcareInstitutionService;
import com.pixeon.healthcare.usecases.getExamById.exception.NoBalanceToConsultingExamException;
import com.pixeon.healthcare.usecases.getvalueconfigapplication.ApplicationConfigService;

import java.math.BigDecimal;

public class GetExamById {

    private ExamService examService;
    private ApplicationConfigService applicationConfigService;
    private HealthcareInstitutionService institutionService;

    public GetExamById(ApplicationConfigService applicationConfigService, ExamService examService, HealthcareInstitutionService institutionService) {
        this.applicationConfigService = applicationConfigService;
        this.examService = examService;
        this.institutionService = institutionService;
    }

    public ExamModel get(int examId) {
        BigDecimal valueForConsultingExam = applicationConfigService.getValueForConsultingExam();
        HealthcareInstitution currentInstitution = institutionService.getCurrentInstitution();

        ExamModel exam = examService.getExameById(examId);
        checkIfInstitutionOwnsExam(currentInstitution, exam.getHealthcareInstitution());
        checkIfInstitutionHaveEnoughBalance(valueForConsultingExam, currentInstitution);
        chargeValueForConsultingExam(valueForConsultingExam, currentInstitution);
        updateInstitutionAfterConsultingExam(currentInstitution, exam);

        exam.setHealthcareInstitution(currentInstitution);
        return exam;
    }

    private void checkIfInstitutionOwnsExam(HealthcareInstitution currentInstitution, HealthcareInstitution examInstitution) {
        if (!currentInstitution.equals(examInstitution)) {
            throw new InstitutionDoesNotOwnExamException();
        }
    }

    private void checkIfInstitutionHaveEnoughBalance(BigDecimal valueForConsultingExam, HealthcareInstitution currentInstitution) {
        new CheckBalanceInstitution<>(new NoBalanceToConsultingExamException()).check(currentInstitution, valueForConsultingExam);
    }

    private void updateInstitutionAfterConsultingExam(HealthcareInstitution institution, ExamModel exam) {
//        chargeValueForConsultingExam(institution);
        institutionService.update(institution);
//        exam.setHealthcareInstitution(institution);
    }

    private void chargeValueForConsultingExam(BigDecimal valueForConsultingExam, HealthcareInstitution institution) {
        institution.subtractCoins(valueForConsultingExam);
    }
}
