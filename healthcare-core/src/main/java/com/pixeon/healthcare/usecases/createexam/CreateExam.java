package com.pixeon.healthcare.usecases.createexam;

import com.pixeon.healthcare.domain.models.ExamModel;
import com.pixeon.healthcare.domain.models.HealthcareInstitutionDTO;
import com.pixeon.healthcare.usecases.createexam.exception.CreateExamFieldEmptyException;
import com.pixeon.healthcare.usecases.createexam.exception.NoBalanceToCreateExamException;
import com.pixeon.healthcare.usecases.createhealthcareInstitution.HealthcareInstitutionFactory;
import com.pixeon.healthcare.usecases.getvalueconfigapplication.ApplicationConfigFactory;

import java.math.BigDecimal;

public class CreateExam {

    private ExamService examService;
    private HealthcareInstitutionFactory institutionService;
    private ApplicationConfigFactory applicationConfigFactory;

    public CreateExam(ExamService examService, HealthcareInstitutionFactory institutionService, ApplicationConfigFactory applicationConfigFactory) {
        this.examService = examService;
        this.institutionService = institutionService;
        this.applicationConfigFactory = applicationConfigFactory;
    }

    public ExamModel create(ExamModel examModel) {
        BigDecimal valueCreateExam = applicationConfigFactory.getValueCreateExam();
        HealthcareInstitutionDTO institution = institutionService.getCurrentInstitution();
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

    private void checkIfInstitutionHaveEnoughBalance(BigDecimal valueCreateExam, HealthcareInstitutionDTO currentInstitution) {
        new CheckBalanceInstitution<>(new NoBalanceToCreateExamException()).check(currentInstitution, valueCreateExam);
    }

    private void updateInstitutionAfterConsultingExam(HealthcareInstitutionDTO institution, ExamModel exam) {
        institutionService.update(institution);
    }

    private void chargeValueForConsultingExam(BigDecimal valueCreateExam, HealthcareInstitutionDTO institution) {
        institution.subtractCoins(valueCreateExam);
    }
}
