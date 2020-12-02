package com.pixeon.healthcare.domain.usecase.updateexam.impl;

import com.pixeon.healthcare.domain.config.exception.CreateExamFieldEmptyException;
import com.pixeon.healthcare.domain.config.exception.IdCantNullException;
import com.pixeon.healthcare.domain.config.exception.InstitutionDoesNotOwnExamException;
import com.pixeon.healthcare.domain.config.exception.InstitutionNotFoundException;
import com.pixeon.healthcare.domain.model.ExamModel;
import com.pixeon.healthcare.domain.model.HealthcareInstitutionModel;
import com.pixeon.healthcare.domain.usecase.createexam.ExamGateway;
import com.pixeon.healthcare.domain.usecase.createhealthcareinstitution.HealthcareInstitutionGateway;
import com.pixeon.healthcare.domain.usecase.updateexam.UpdateExam;

public class UpdateExamImpl implements UpdateExam {

    private ExamGateway examGateway;
    private HealthcareInstitutionGateway institutionService;

    public UpdateExamImpl(HealthcareInstitutionGateway institutionService, ExamGateway examGateway) {
        this.institutionService = institutionService;
        this.examGateway = examGateway;
    }

    public ExamModel update(ExamModel examModel) {
        validFields(examModel);
        HealthcareInstitutionModel examInstitution = getInstitutionOfExam(examModel);
        checkIfInstitutionOwnsExam(examInstitution);
        return examGateway.update(examModel);
    }

    private HealthcareInstitutionModel getInstitutionOfExam(ExamModel examModel) {
        HealthcareInstitutionModel examInstitution = institutionService.getInstitutionForExamBy(examModel.getId());
        if (examInstitution == null) {
            throw new InstitutionNotFoundException();
        }
        return examInstitution;
    }

    private void checkIfInstitutionOwnsExam(HealthcareInstitutionModel examInstitution) {
        HealthcareInstitutionModel currentInstitution = institutionService.getCurrentInstitution();
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
