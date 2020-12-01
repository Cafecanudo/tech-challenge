package com.pixeon.healthcare.usecases.updateexam;

import com.pixeon.healthcare.domain.exception.InstitutionDoesNotOwnExamException;
import com.pixeon.healthcare.domain.exception.InstitutionNotFoundException;
import com.pixeon.healthcare.domain.models.ExamModel;
import com.pixeon.healthcare.domain.models.HealthcareInstitutionDTO;
import com.pixeon.healthcare.usecases.createexam.ExamService;
import com.pixeon.healthcare.usecases.createexam.exception.CreateExamFieldEmptyException;
import com.pixeon.healthcare.usecases.createhealthcareInstitution.HealthcareInstitutionFactory;
import com.pixeon.healthcare.usecases.updateexam.exception.IdCantNullException;

public class UpdateExam {

    private ExamService examService;
    private HealthcareInstitutionFactory institutionService;

    public UpdateExam(HealthcareInstitutionFactory institutionService, ExamService examService) {
        this.institutionService = institutionService;
        this.examService = examService;
    }

    public ExamModel update(ExamModel examModel) {
        validFields(examModel);
        HealthcareInstitutionDTO examInstitution = getInstitutionOfExam(examModel);
        checkIfInstitutionOwnsExam(examInstitution);
        return examService.update(examModel);
    }

    private HealthcareInstitutionDTO getInstitutionOfExam(ExamModel examModel) {
        HealthcareInstitutionDTO examInstitution = institutionService.getInstitutionForExamBy(examModel.getId());
        if (examInstitution == null) {
            throw new InstitutionNotFoundException();
        }
        return examInstitution;
    }

    private void checkIfInstitutionOwnsExam(HealthcareInstitutionDTO examInstitution) {
        HealthcareInstitutionDTO currentInstitution = institutionService.getCurrentInstitution();
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
