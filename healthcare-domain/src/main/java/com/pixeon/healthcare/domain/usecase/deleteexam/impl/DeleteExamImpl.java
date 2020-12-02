package com.pixeon.healthcare.domain.usecase.deleteexam.impl;

import com.pixeon.healthcare.domain.config.exception.ExamNotFoundException;
import com.pixeon.healthcare.domain.config.exception.InstitutionDoesNotOwnExamException;
import com.pixeon.healthcare.domain.config.exception.InstitutionNotFoundException;
import com.pixeon.healthcare.domain.model.ExamModel;
import com.pixeon.healthcare.domain.model.HealthcareInstitutionModel;
import com.pixeon.healthcare.domain.usecase.createexam.ExamGateway;
import com.pixeon.healthcare.domain.usecase.createhealthcareinstitution.HealthcareInstitutionGateway;
import com.pixeon.healthcare.domain.usecase.deleteexam.DeleteExam;

public class DeleteExamImpl implements DeleteExam {

    private ExamGateway examGateway;
    private HealthcareInstitutionGateway institutionService;

    public DeleteExamImpl(HealthcareInstitutionGateway institutionService, ExamGateway examGateway) {
        this.examGateway = examGateway;
        this.institutionService = institutionService;
    }

    public boolean delete(int examId) {
        ExamModel examModel = getExam(examId);
        HealthcareInstitutionModel examInstitution = getInstitutionOfExam(examModel);
        checkIfInstitutionOwnsExam(examInstitution);
        return examGateway.delete(examModel);
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

    private ExamModel getExam(int examId) {
        ExamModel examModel = this.examGateway.getExameById(examId);
        if (examModel == null) {
            throw new ExamNotFoundException();
        }
        return examModel;
    }

}
