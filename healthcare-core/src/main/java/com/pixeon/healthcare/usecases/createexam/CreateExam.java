package com.pixeon.healthcare.usecases.createexam;

import com.pixeon.healthcare.domain.models.ExamModel;
import com.pixeon.healthcare.domain.models.HealthcareInstitution;
import com.pixeon.healthcare.usecases.createexam.exception.CreateExamFieldEmptyException;
import com.pixeon.healthcare.usecases.createexam.exception.NoBalanceToCreateExamException;
import com.pixeon.healthcare.usecases.createhealthcareInstitution.HealthcareInstitutionService;
import com.pixeon.healthcare.usecases.getvalueconfigapplication.ApplicationConfigService;

import java.math.BigDecimal;

public class CreateExam {

    private ExamService examService;
    private HealthcareInstitutionService institutionService;
    private ApplicationConfigService applicationConfigService;

    public CreateExam(ExamService examService, HealthcareInstitutionService institutionService, ApplicationConfigService applicationConfigService) {
        this.examService = examService;
        this.institutionService = institutionService;
        this.applicationConfigService = applicationConfigService;
    }

    public ExamModel create(ExamModel examModel) {
        BigDecimal valueCreateExam = applicationConfigService.getValueCreateExam();
        HealthcareInstitution institution = institutionService.getCurrentInstitution();
        examModel.setHealthcareInstitution(institution);

        validExamField(examModel);
        checkIfInstitutionHaveEnoughBalance(valueCreateExam, institution);
        chargeValueForConsultingExam(valueCreateExam, institution);
        updateInstitutionAfterConsultingExam(institution, examModel);

        return examService.save(examModel);
    }

    private void validExamField(ExamModel examModel) {
        if (examModel.isEmptyFields()) {
            throw new CreateExamFieldEmptyException();
        }
    }

    private void checkIfInstitutionHaveEnoughBalance(BigDecimal valueCreateExam, HealthcareInstitution currentInstitution) {
        new CheckBalanceInstitution<>(new NoBalanceToCreateExamException()).check(currentInstitution, valueCreateExam);
    }

    private void updateInstitutionAfterConsultingExam(HealthcareInstitution institution, ExamModel exam) {
        institutionService.update(institution);
    }

    private void chargeValueForConsultingExam(BigDecimal valueCreateExam, HealthcareInstitution institution) {
        institution.subtractCoins(valueCreateExam);
    }
}
