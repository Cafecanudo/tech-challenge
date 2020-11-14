package com.pixeon.healthcare.usecases.createexam;

import com.pixeon.healthcare.models.ExamModel;
import com.pixeon.healthcare.models.HealthcareInstitution;
import com.pixeon.healthcare.usecases.applicationconfig.ApplicationConfigService;
import com.pixeon.healthcare.usecases.createexam.exception.CreateExamFieldEmptyException;
import com.pixeon.healthcare.usecases.createexam.exception.NoBalanceToCreateExamException;
import com.pixeon.healthcare.usecases.createexam.exception.NoFoundHealthcareInstituitionException;
import com.pixeon.healthcare.usecases.createhealthcareInstitution.HealthcareInstitutionService;

import java.math.BigDecimal;

public class CreateExam {

    private static final double WITHOUT_BALANCE = 0.0;
    private ExamService examService;
    private HealthcareInstitutionService institutionService;
    private ApplicationConfigService applicationConfigService;

    public CreateExam(ExamService examService, HealthcareInstitutionService institutionService, ApplicationConfigService applicationConfigService) {
        this.examService = examService;
        this.institutionService = institutionService;
        this.applicationConfigService = applicationConfigService;
    }

    public ExamModel create(int healthcareInstitutionId, ExamModel examModel) {
        HealthcareInstitution institution = getHealthcareInstitution(healthcareInstitutionId);
        examModel.setHealthcareInstitution(institution);

        validExamField(examModel);
        chargeValueForCreateExam(institution);
        return examService.save(examModel);
    }

    private HealthcareInstitution getHealthcareInstitution(int healthcareInstitutionId) {
        HealthcareInstitution institution = institutionService.get(healthcareInstitutionId);
        if (institution == null){
            throw new NoFoundHealthcareInstituitionException();
        }
        return institution;
    }

    private void validExamField(ExamModel examModel) {
        if (examModel.isEmptyFields()){
            throw new CreateExamFieldEmptyException();
        }
    }

    private void chargeValueForCreateExam(HealthcareInstitution institution) {
        BigDecimal valueForCreateExam = applicationConfigService.getValueCreateExam();
        checkBalance(institution, valueForCreateExam);
        institution.subtractCoins(valueForCreateExam);
    }

    private void checkBalance(HealthcareInstitution institution, BigDecimal valueForCreateExam) {
        if (institution.getCoins().doubleValue() <= WITHOUT_BALANCE) {
            throw new NoBalanceToCreateExamException();
        }

        if (institution.getCoins().doubleValue() < valueForCreateExam.doubleValue()) {
            throw new NoBalanceToCreateExamException();
        }
    }
}
