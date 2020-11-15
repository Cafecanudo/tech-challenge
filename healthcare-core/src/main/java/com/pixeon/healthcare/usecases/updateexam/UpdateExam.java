package com.pixeon.healthcare.usecases.updateexam;

import com.pixeon.healthcare.domain.exception.InstitutionDoesNotOwnExamException;
import com.pixeon.healthcare.domain.exception.InstitutionNotFoundException;
import com.pixeon.healthcare.domain.models.ExamModel;
import com.pixeon.healthcare.domain.models.HealthcareInstitution;
import com.pixeon.healthcare.usecases.createexam.ExamService;
import com.pixeon.healthcare.usecases.createexam.exception.CreateExamFieldEmptyException;
import com.pixeon.healthcare.usecases.createhealthcareInstitution.HealthcareInstitutionService;
import com.pixeon.healthcare.usecases.updateexam.exception.IdCantNullException;

public class UpdateExam {

    private ExamService examService;
    private HealthcareInstitutionService institutionService;

    public UpdateExam(HealthcareInstitutionService institutionService, ExamService examService) {
        this.institutionService = institutionService;
        this.examService = examService;
    }

    public ExamModel update(ExamModel examModel) {
        validFields(examModel);
        HealthcareInstitution examInstitution = getInstitutionOfExam(examModel);
        checkIfInstitutionOwnsExam(examInstitution);
        return examService.update(examModel);
    }

    private HealthcareInstitution getInstitutionOfExam(ExamModel examModel) {
        HealthcareInstitution examInstitution = institutionService.getInstitutionForExamBy(examModel.getId());
        if (examInstitution == null) {
            throw new InstitutionNotFoundException();
        }
        return examInstitution;
    }

    private void checkIfInstitutionOwnsExam(HealthcareInstitution examInstitution) {
        HealthcareInstitution currentInstitution = institutionService.getCurrentInstitution();
        if (!currentInstitution.equals(examInstitution)) {
            throw new InstitutionDoesNotOwnExamException();
        }
    }

    private void validFields(ExamModel examModel) {
        if (examModel.isNullId()) {
            throw new IdCantNullException();
        }
        if (examModel.isEmptyFields()) {
            throw new CreateExamFieldEmptyException();
        }
    }
}
